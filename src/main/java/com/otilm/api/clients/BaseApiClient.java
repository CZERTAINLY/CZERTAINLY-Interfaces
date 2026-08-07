package com.otilm.api.clients;

import com.otilm.api.exception.*;
import com.otilm.api.model.client.attribute.ResponseAttribute;
import com.otilm.api.model.common.attribute.v2.content.FileAttributeContentV2;
import com.otilm.api.model.common.attribute.v2.content.SecretAttributeContentV2;
import com.otilm.api.model.common.attribute.v2.content.StringAttributeContentV2;
import com.otilm.api.model.common.error.ProblemDetailExtended;
import com.otilm.api.model.core.connector.ConnectorStatus;
import com.otilm.core.util.AttributeDefinitionUtils;
import com.otilm.core.util.KeyStoreUtils;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.buffer.DataBufferLimitException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.*;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

public abstract class BaseApiClient {
    private static final Logger logger = LoggerFactory.getLogger(BaseApiClient.class);

    // Basic auth attribute names
    public static final String ATTRIBUTE_USERNAME = "username";
    public static final String ATTRIBUTE_PASSWORD = "password";

    // Certificate attribute names
    public static final String ATTRIBUTE_KEYSTORE_TYPE = "keyStoreType";
    public static final String ATTRIBUTE_KEYSTORE = "keyStore";
    public static final String ATTRIBUTE_KEYSTORE_PASSWORD = "keyStorePassword";
    public static final String ATTRIBUTE_TRUSTSTORE_TYPE = "trustStoreType";
    public static final String ATTRIBUTE_TRUSTSTORE = "trustStore";
    public static final String ATTRIBUTE_TRUSTSTORE_PASSWORD = "trustStorePassword";

    // API key attribute names
    public static final String ATTRIBUTE_API_KEY_HEADER = "apiKeyHeader";
    public static final String ATTRIBUTE_API_KEY = "apiKey";

    // Pool hygiene (not deployment-specific): evict idle and age-cap connections so a server-closed
    // keep-alive is not reused (PrematureCloseException).
    private static final Duration POOL_MAX_IDLE = Duration.ofSeconds(30);
    private static final Duration POOL_MAX_LIFE = Duration.ofMinutes(5);
    private static final Duration POOL_EVICT_INTERVAL = Duration.ofSeconds(30);
    private static final Duration POOL_DISPOSE_INTERVAL = Duration.ofSeconds(120);
    private static final Duration POOL_DISPOSE_AFTER = Duration.ofSeconds(300);

    // Bound for the cause-chain walk in findInCauseChain. Chains this deep do not occur in practice;
    // the cap is there so a cyclic chain cannot spin forever inside an exception handler.
    private static final int MAX_CAUSE_CHAIN_DEPTH = 32;

    // Applied once (first prepareWebClient wins). The tuned HttpClient is the single source the
    // CERTIFICATE path derives from, built lazily so a tuned deployment never leaves an orphaned
    // default ConnectionProvider (which its background task would pin forever).
    private static volatile ClientTuning appliedTuning;
    private static volatile ConnectionProvider connectionProvider;
    private static volatile HttpClient baseHttpClient;

    // Per-connector CERTIFICATE WebClients keyed by UUID; authMaterialHash invalidates on credential
    // rotation. Caching the built WebClient (not just the SslContext) keeps the Reactor pool key
    // stable — a fresh SslContext per request means a new SslProvider, hence no connection reuse.
    // Process-wide: assumes all clients share one base WebClient + defaultTrustManagers (true in core).
    private static final Map<String, CachedCertClient> certClientCache = new ConcurrentHashMap<>();

    private record CachedCertClient(String authHash, WebClient webClient) {
    }

    protected WebClient webClient;

    protected TrustManager[] defaultTrustManagers;

    protected BaseApiClient() {

    }

    protected BaseApiClient(WebClient webClient, TrustManager[] defaultTrustManagers) {
        this.webClient = webClient;
        this.defaultTrustManagers = defaultTrustManagers;
    }

    public WebClient.RequestBodyUriSpec prepareRequest(HttpMethod method, ApiClientConnectorInfo connector, boolean validateConnectorStatus) {
        if (validateConnectorStatus) {
            validateConnectorStatus(connector.getStatus());
        }
        WebClient.RequestBodySpec request;

        // for backward compatibility
        if (connector.getAuthType() == null) {
            request = webClient.method(method);
            return (WebClient.RequestBodyUriSpec) request;
        }

        List<ResponseAttribute> authAttributes = connector.getAuthAttributes();

        switch (connector.getAuthType()) {
            case NONE:
                request = webClient.method(method);
                break;
            case BASIC:
                List<StringAttributeContentV2> usernameContent = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_USERNAME, authAttributes, StringAttributeContentV2.class);
                List<SecretAttributeContentV2> passwordContent = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_PASSWORD, authAttributes, SecretAttributeContentV2.class);

                if (usernameContent == null || usernameContent.isEmpty() || passwordContent == null || passwordContent.isEmpty())
                    throw new IllegalArgumentException("Missing username or password in authentication");

                String usernameValue = usernameContent.get(0).getData();
                String passwordValue = passwordContent.get(0).getData().getSecret();

                request = webClient
                        .method(method)
                        .headers(h -> h.setBasicAuth(usernameValue, passwordValue));
                break;
            case CERTIFICATE:
                request = certificateWebClient(connector).method(method);
                break;
            case API_KEY:
                List<StringAttributeContentV2> apiKeyHeaderContent = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_API_KEY_HEADER, authAttributes, StringAttributeContentV2.class);
                List<SecretAttributeContentV2> apiKeyContent = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_API_KEY, authAttributes, SecretAttributeContentV2.class);

                if (apiKeyHeaderContent == null || apiKeyHeaderContent.isEmpty() || apiKeyContent == null || apiKeyContent.isEmpty())
                    throw new IllegalArgumentException("Missing API Key or API Key header in authentication");

                String apiKeyHeaderValue = apiKeyHeaderContent.get(0).getData();
                String apiKeyValue = apiKeyContent.get(0).getData().getSecret();

                request = webClient
                        .method(method)
                        .headers(h -> h.set(apiKeyHeaderValue, apiKeyValue));
                break;
            case JWT:
                throw new UnsupportedOperationException("JWT is unimplemented");
            default:
                throw new IllegalArgumentException("Unknown auth type " + connector.getAuthType());
        }

        return (WebClient.RequestBodyUriSpec) request;
    }

    public void validateConnectorStatus(ConnectorStatus connectorStatus) throws ValidationException {
        if (connectorStatus == ConnectorStatus.WAITING_FOR_APPROVAL) {
            throw new ValidationException(ValidationError.create("Connector has invalid status: " + connectorStatus.getLabel()));
        }
    }

    /**
     * CERTIFICATE-auth WebClient for a connector, cached while its auth material is unchanged and
     * derived from the shared tuned {@link #baseHttpClient} so it inherits the pool and timeouts.
     */
    WebClient certificateWebClient(ApiClientConnectorInfo connector) {
        // Resolve the base client (may take the class lock) before compute(), so the map-bin lock
        // never nests the class lock — the reverse of resetConnectorClientForTest, a deadlock risk.
        HttpClient httpClient = baseHttpClient();
        String uuid = connector.getUuid();
        if (uuid == null) {
            // Not cacheable without a stable key; build per request.
            return buildCertificateWebClient(connector, httpClient);
        }
        String authHash = authMaterialHash(connector.getAuthAttributes());
        // compute() builds once per (uuid, authHash); concurrent first-callers serialize on the bin
        // rather than each building a distinct SslContext (two pool keys for one host).
        return certClientCache.compute(uuid, (key, existing) ->
                existing != null && existing.authHash().equals(authHash)
                        ? existing
                        : new CachedCertClient(authHash, buildCertificateWebClient(connector, httpClient))
        ).webClient();
    }

    private WebClient buildCertificateWebClient(ApiClientConnectorInfo connector, HttpClient httpClient) {
        SslContext sslContext = createSslContext(connector.getAuthAttributes());
        HttpClient certHttpClient = httpClient.secure(spec -> spec.sslContext(sslContext));
        return webClient.mutate()
                .clientConnector(new ReactorClientHttpConnector(certHttpClient))
                .build();
    }

    /**
     * Drop the cached CERTIFICATE WebClient for a connector. Core calls this on connector delete or
     * auth change so the process-wide cache does not retain a stale client and its key material.
     */
    public static void evictCertificateClient(String connectorUuid) {
        if (connectorUuid != null) {
            certClientCache.remove(connectorUuid);
        }
    }

    private SslContext createSslContext(List<ResponseAttribute> attributes) {
        try {
            SslContextBuilder sslContextBuilder = SslContextBuilder.forClient();

            KeyManager km = null;
            List<FileAttributeContentV2> keyStoreDataList = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_KEYSTORE, attributes, FileAttributeContentV2.class);
            FileAttributeContentV2 keyStoreData = keyStoreDataList != null && !keyStoreDataList.isEmpty() ? keyStoreDataList.get(0) : null;
            if (keyStoreData != null && !keyStoreData.getData().getContent().isEmpty()) {
                KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

                String keyStorePassword = getStorePassword(attributes, ATTRIBUTE_KEYSTORE_PASSWORD);
                String keyStoreType = getStoreType(attributes, ATTRIBUTE_KEYSTORE_TYPE);
                byte[] keyStoreBytes = Base64.getDecoder().decode(keyStoreData.getData().getContent());

                kmf.init(KeyStoreUtils.bytes2KeyStore(keyStoreBytes, keyStorePassword, keyStoreType), keyStorePassword != null ? keyStorePassword.toCharArray() : null);
                km = kmf.getKeyManagers()[0];
            }

            sslContextBuilder.keyManager(km);

            TrustManager tm;
            List<FileAttributeContentV2> trustStoreDataList = AttributeDefinitionUtils.getAttributeContent(ATTRIBUTE_TRUSTSTORE, attributes, FileAttributeContentV2.class);
            FileAttributeContentV2 trustStoreData = trustStoreDataList != null && !trustStoreDataList.isEmpty() ? trustStoreDataList.get(0) : null;
            if (trustStoreData != null && !trustStoreData.getData().getContent().isEmpty()) {
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

                String trustStorePassword = getStorePassword(attributes, ATTRIBUTE_TRUSTSTORE_PASSWORD);
                String trustStoreType = getStoreType(attributes, ATTRIBUTE_TRUSTSTORE_TYPE);
                byte[] trustStoreBytes = Base64.getDecoder().decode(trustStoreData.getData().getContent());

                tmf.init(KeyStoreUtils.bytes2KeyStore(trustStoreBytes, trustStorePassword, trustStoreType));
                tm = tmf.getTrustManagers()[0];
            } else {
                // set default trustManager
                tm = defaultTrustManagers[0];
            }

            sslContextBuilder.trustManager(tm);

            return sslContextBuilder.protocols("TLSv1.2").build();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize SslContext.", e);
        }
    }

    /**
     * Content hash of the keystore/truststore material — the cache-invalidation token for
     * {@link #certClientCache}. Hashing keeps secrets out of the map keys.
     */
    private static String authMaterialHash(List<ResponseAttribute> attributes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Length-prefix each field fed straight to the digest (no concatenated keystore copy),
            // so absent/adjacent values cannot alias.
            updateField(digest, getStoreType(attributes, ATTRIBUTE_KEYSTORE_TYPE));
            updateField(digest, storeContent(attributes, ATTRIBUTE_KEYSTORE));
            updateField(digest, getStorePassword(attributes, ATTRIBUTE_KEYSTORE_PASSWORD));
            updateField(digest, getStoreType(attributes, ATTRIBUTE_TRUSTSTORE_TYPE));
            updateField(digest, storeContent(attributes, ATTRIBUTE_TRUSTSTORE));
            updateField(digest, getStorePassword(attributes, ATTRIBUTE_TRUSTSTORE_PASSWORD));
            return Base64.getEncoder().encodeToString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }

    private static void updateField(MessageDigest digest, String value) {
        if (value == null) {
            digest.update((byte) 0);
            return;
        }
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        digest.update((byte) 1);
        digest.update((byte) (bytes.length >>> 24));
        digest.update((byte) (bytes.length >>> 16));
        digest.update((byte) (bytes.length >>> 8));
        digest.update((byte) bytes.length);
        digest.update(bytes);
    }

    private static String storeContent(List<ResponseAttribute> attributes, String name) {
        List<FileAttributeContentV2> list = AttributeDefinitionUtils.getAttributeContent(name, attributes, FileAttributeContentV2.class);
        if (list == null || list.isEmpty()) {
            return null;
        }
        var data = list.get(0).getData();
        return data != null ? data.getContent() : null;
    }

    private static String getStoreType(List<ResponseAttribute> attributes, String name) {
        List<StringAttributeContentV2> keyStoreTypeList = AttributeDefinitionUtils.getAttributeContent(name, attributes, StringAttributeContentV2.class);
        return keyStoreTypeList != null && !keyStoreTypeList.isEmpty() ? keyStoreTypeList.get(0).getData() : null;
    }

    private static String getStorePassword(List<ResponseAttribute> attributes, String attributeName) {
        List<SecretAttributeContentV2> list = AttributeDefinitionUtils.getAttributeContent(attributeName, attributes, SecretAttributeContentV2.class);
        return list != null && !list.isEmpty() ? list.get(0).getData().getSecret() : null;
    }

    private static final ParameterizedTypeReference<List<String>> ERROR_LIST_TYPE_REF = new ParameterizedTypeReference<>() {
    };

    /**
     * Build the shared connector WebClient with default tuning. Callers that do not configure tuning
     * (tests, and any consumer without deployment config) get {@link ClientTuning#defaults()}.
     */
    public static WebClient prepareWebClient() {
        return prepareWebClient(ClientTuning.defaults());
    }

    /**
     * Build the shared WebClient with the given tuning. First call wins; a later call with different
     * tuning is ignored (warned) so the live ConnectionProvider is not orphaned — matters under
     * Spring test-context caching.
     */
    public static synchronized WebClient prepareWebClient(ClientTuning tuning) {
        Objects.requireNonNull(tuning, "tuning must not be null");
        if (appliedTuning == null) {
            appliedTuning = tuning;
            connectionProvider = buildConnectionProvider(tuning);
            baseHttpClient = buildHttpClient(connectionProvider, tuning);
        } else if (!appliedTuning.equals(tuning)) {
            logger.warn("Connector WebClient already tuned with {}; ignoring differing request {}", appliedTuning, tuning);
        }
        // appliedTuning (the tuning that won), not the tuning passed to this call, so every knob —
        // pool sizing and maxInMemorySize alike — stays consistent with the single baseHttpClient
        // built above, even when a later, differently-tuned caller was warned-and-ignored.
        return buildWebClient(baseHttpClient, appliedTuning);
    }

    /** Lazily initialize with defaults if a client is used before any prepareWebClient call. */
    private static synchronized HttpClient baseHttpClient() {
        if (baseHttpClient == null) {
            prepareWebClient();
        }
        return baseHttpClient;
    }

    private static ConnectionProvider buildConnectionProvider(ClientTuning tuning) {
        return ConnectionProvider.builder("connector")
                .maxConnections(tuning.maxConnections())
                .pendingAcquireMaxCount(Math.multiplyExact(tuning.maxConnections(), 2))
                .pendingAcquireTimeout(tuning.pendingAcquireTimeout())
                .maxIdleTime(POOL_MAX_IDLE)
                .maxLifeTime(POOL_MAX_LIFE)
                .evictInBackground(POOL_EVICT_INTERVAL)
                .disposeInactivePoolsInBackground(POOL_DISPOSE_INTERVAL, POOL_DISPOSE_AFTER)
                .lifo()
                .build();
    }

    private static HttpClient buildHttpClient(ConnectionProvider provider, ClientTuning tuning) {
        // responseTimeout is reactor-netty's per-request read guard (idle-safe), failing fast on a
        // non-responding connector; a mid-body stall is bounded by the caller's transaction timeout.
        return HttpClient.create(provider)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Math.toIntExact(tuning.connectTimeout().toMillis()))
                .responseTimeout(tuning.responseTimeout());
    }

    private static WebClient buildWebClient(HttpClient httpClient, ClientTuning tuning) {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(tuning.maxInMemorySize()))
                .build();
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .filter(ExchangeFilterFunction.ofResponseProcessor(BaseApiClient::handleHttpExceptions))
                .exchangeStrategies(strategies)
                .build();
    }

    /**
     * Reset the static tuning and CERTIFICATE-client cache to defaults. Test-only seam so cases that
     * apply custom tuning do not leak into subsequent cases in the same JVM.
     */
    static synchronized void resetConnectorClientForTest() {
        if (connectionProvider != null) {
            connectionProvider.dispose();
        }
        connectionProvider = null;
        baseHttpClient = null;
        appliedTuning = null;
        certClientCache.clear();
    }

    /**
     * Block on a {@code Mono<ResponseEntity<T>>} and guarantee a non-null response entity.
     * {@code Mono.block()} returns null when the publisher completes empty; dereferencing that
     * would throw an opaque NPE. Surfacing a clear IllegalStateException at the call site makes
     * a misbehaving connector (no response) diagnosable instead of producing a bare NPE.
     *
     * <p><b>Known discrepancy with the MQ clients, not resolvable here.</b> The MQ discovery client's
     * equivalent guard throws {@code ConnectorException(message, connector)} — checked, so it travels
     * the channel every client method already declares, and attributed to the connector that produced
     * the failure. That is the better shape, and this method (with {@link #requireBody}) deliberately
     * does not adopt it. The {@link IllegalStateException} thrown here is unchecked, so it escapes
     * methods declaring {@code throws ConnectorException} and falls through
     * {@link #processRequest}'s catch-all unmapped and unattributed to any connector.
     *
     * <p>Switching to a checked {@code ConnectorException} does not compile. Every call site of these
     * two methods across this library — 14 in {@code v3.CertificateApiClient}, 9 in
     * {@code discovery.v2.DiscoveryApiClient}, 5 in {@code v3.AuthorityApiClient}, 3 in
     * {@code v2.AttributesApiClient} — sits inside the {@link Function} lambda handed to
     * {@link #processRequest}, and {@code Function.apply} declares no checked exception, so each one
     * fails with "unreported exception ... must be caught or declared to be thrown". Fixing that means
     * changing {@code processRequest} to take a checked-exception-throwing functional interface, and
     * {@code processRequest} is public API of the contract Core and every external connector compile
     * against: an added overload would make every existing lambda call site ambiguous, and a replaced
     * parameter type would break any caller that passes a {@code Function} value rather than a lambda.
     * That is a deliberate, separately-planned contract change, not a cleanup to slip in here.
     *
     * <p>Until then a caller that must distinguish a bodiless response has to catch
     * {@link IllegalStateException} alongside {@code ConnectorException}, and the connector's identity
     * has to come from the call site rather than from the exception.
     */
    protected static <T> ResponseEntity<T> requireResponse(Mono<ResponseEntity<T>> mono, String context) {
        ResponseEntity<T> entity = mono.block();
        if (entity == null) {
            throw new IllegalStateException("No response received from connector for " + context);
        }
        return entity;
    }

    /**
     * Like {@link #requireResponse} but also requires a non-null body — for endpoints whose 2xx
     * response must carry a payload (attribute lists, operation status, identify, CRL, CA certs).
     * An empty body on success is a connector contract violation; fail clearly rather than
     * returning null to the caller (which would NPE later, far from the cause).
     *
     * <p>Throws an unchecked, connector-less {@link IllegalStateException} where the MQ discovery
     * client's counterpart throws a checked, connector-attributed {@code ConnectorException}. The
     * discrepancy is known and stands for the reason set out on {@link #requireResponse}: the change
     * cannot compile without first reworking {@link #processRequest}'s public functional interface.
     */
    protected static <T> T requireBody(Mono<ResponseEntity<T>> mono, String context) {
        ResponseEntity<T> entity = requireResponse(mono, context);
        if (entity.getBody() == null) {
            throw new IllegalStateException("Connector returned an empty body for " + context);
        }
        return entity.getBody();
    }

    public static <T, R> R processRequest(Function<T, R> func, T request, ApiClientConnectorInfo connector) throws ConnectorException {
        try {
            return func.apply(request);
        } catch (Exception e) {
            Throwable unwrapped = Exceptions.unwrap(e);
            if (unwrapped instanceof ConnectorProblemException pde) {
                pde.setConnector(connector);
                throw pde;
            } else if (unwrapped instanceof IOException
                    || unwrapped instanceof WebClientRequestException
                    || unwrapped instanceof io.netty.handler.timeout.TimeoutException
                    || unwrapped instanceof TimeoutException
                    || isPoolAcquireExhausted(unwrapped)) {
                // Connect, response, and pool-acquire failures. Netty timeouts aren't IOExceptions and
                // the pool pending-limit is a plain RuntimeException, so match them explicitly. Log
                // type+message; the full cause rides on the exception thrown below.
                logger.error("Connector {} communication failure: {}", connector.getName(), unwrapped.toString());
                throw new ConnectorCommunicationException("Error in connector %s communication. URL: %s".formatted(connector.getName(), connector.getUrl()), unwrapped, connector);
            } else if (isOversizedResponse(unwrapped)) {
                // The response existed (2xx or otherwise) but the codec's maxInMemorySize (ClientTuning)
                // was exceeded while decoding its body — a connector fault (oversized/malicious page),
                // not a communication failure. Unmapped, this would escape as WebClientResponseException
                // (or a bare DataBufferLimitException), which callers declaring `throws ConnectorException`
                // cannot catch, which never gets attributed to a connector via setConnector, and whose
                // full stack a hostile connector could repeatedly trigger at ERROR level.
                logger.error("Connector {} response exceeded the configured read limit: {}", connector.getName(), unwrapped.toString());
                ConnectorServerException cse = new ConnectorServerException(
                        "Connector %s response exceeded the configured read limit. URL: %s".formatted(connector.getName(), connector.getUrl()),
                        unwrapped,
                        upstreamStatus(unwrapped));
                cse.setConnector(connector);
                throw cse;
            } else if (unwrapped instanceof ConnectorException ce) {
                ce.setConnector(connector);
                throw ce;
            } else if (unwrapped instanceof PlatformException) {
                // Expected business error (e.g. connector 422); caller handles it. Skip the noisy
                // Reactor-enhanced stacktrace, keep the message at DEBUG.
                logger.debug("Connector {} request rejected: {}", connector.getName(), unwrapped.getMessage());
                // Throw the unwrapped exception so callers catch the domain type (e.g. ValidationException)
                // rather than a Reactor wrapper; fall back to the original when it is not re-throwable.
                if (unwrapped instanceof RuntimeException re) {
                    throw re;
                }
                throw e;
            } else {
                logger.error(unwrapped.getMessage(), unwrapped);
                throw e;
            }
        }
    }

    /**
     * True when a {@link DataBufferLimitException} appears anywhere in {@code t}'s cause chain — a
     * codec {@code maxInMemorySize} breach, whatever wrapped it.
     *
     * <p>{@code WebClient.retrieve()} wraps a body-decode failure into a
     * {@link WebClientResponseException} so the failure still carries the response's status and
     * headers, and the bare form reaches here when some other codepath surfaces it unwrapped — but
     * those are not the only two shapes. Spring's {@code Jackson2JsonDecoder} raises a
     * {@code DecodingException} around a decode failure as well (the reason
     * {@link #isJsonTypeResolutionFailure} has to look one level down), so a breach can sit two or
     * more levels deep. Matching only the bare form and one level of wrapping let those escape to
     * {@code processRequest}'s catch-all and surface as a Spring-internal type, which is the exact
     * gap this branch exists to close, so the whole chain is walked.
     */
    private static boolean isOversizedResponse(Throwable t) {
        return findInCauseChain(t, DataBufferLimitException.class) != null;
    }

    /**
     * The status the connector actually answered with, for a failure raised while decoding its
     * response. Core's {@code ExceptionHandlingAdvice} renders this verbatim as an "Original response
     * code ..." suffix on the operator-facing error, so it must not be invented: a read-limit breach
     * is typically hit on a {@code 200}, and reporting {@code 413 PAYLOAD_TOO_LARGE} there would
     * assert an upstream status that never existed and point operators at a request-size problem
     * rather than at this client's own read cap — which the exception message already names, so
     * carrying the real status loses nothing.
     *
     * <p>{@code WebClient.retrieve()} wraps a body-decode failure into a
     * {@link WebClientResponseException} precisely so the response's status survives, which is where
     * the real status comes from. {@link HttpStatus#BAD_GATEWAY} is the fallback when no such wrapper
     * is in the chain (a bare codec failure carries no status) or when the connector answered a
     * non-standard code {@link HttpStatus} cannot represent: the response was unusable and the
     * connector is upstream of us, which is what 502 says.
     */
    private static HttpStatus upstreamStatus(Throwable t) {
        WebClientResponseException responseException = findInCauseChain(t, WebClientResponseException.class);
        HttpStatus resolved = responseException == null
                ? null
                : HttpStatus.resolve(responseException.getStatusCode().value());
        return resolved != null ? resolved : HttpStatus.BAD_GATEWAY;
    }

    /**
     * The first throwable of the given type in {@code t}'s cause chain, {@code t} itself included, or
     * {@code null} when there is none.
     *
     * <p>The walk is deliberately bounded. Every caller runs inside an exception handler, where an
     * infinite loop on a cyclic cause chain would be a far worse failure than the misclassification
     * the walk prevents — so {@link #MAX_CAUSE_CHAIN_DEPTH} caps a cycle of any length, and the
     * self-cause check short-circuits the common one-node case.
     */
    private static <E extends Throwable> E findInCauseChain(Throwable t, Class<E> type) {
        Throwable current = t;
        for (int depth = 0; current != null && depth < MAX_CAUSE_CHAIN_DEPTH; depth++) {
            if (type.isInstance(current)) {
                return type.cast(current);
            }
            if (current == current.getCause()) {
                return null;
            }
            current = current.getCause();
        }
        return null;
    }

    /**
     * The pool pending-limit exception is a plain RuntimeException (unlike PoolAcquireTimeoutException,
     * a {@link TimeoutException} matched above). Match by name to avoid depending on Reactor-Netty's
     * shaded internal pool type.
     */
    @SuppressWarnings("java:S1872") // intentional name match — instanceof would couple to the shaded type
    private static boolean isPoolAcquireExhausted(Throwable t) {
        return "PoolAcquirePendingLimitException".equals(t.getClass().getSimpleName());
    }

    private static Mono<ClientResponse> handleHttpExceptions(ClientResponse clientResponse) {
        if (clientResponse.statusCode().is2xxSuccessful()) {
            return Mono.just(clientResponse);
        }

        // Check if response is RFC 9457 problem+json format
        String contentType = clientResponse.headers().contentType()
                .map(mediaType -> mediaType.toString().toLowerCase())
                .orElse("");

        if (contentType.contains(MediaType.APPLICATION_PROBLEM_JSON_VALUE)) {
            return handleProblemDetailResponse(clientResponse);
        }
        if (contentType.contains(MediaType.TEXT_HTML_VALUE)) {
            // defaultIfEmpty for the same reason as the legacy branches below: bodyToMono completes
            // empty for a zero-length body, so without it flatMap never runs and the failure escapes
            // unmapped as an IllegalStateException.
            return clientResponse.bodyToMono(String.class)
                    .defaultIfEmpty("")
                    .flatMap(body -> Mono.error(new ConnectorCommunicationException("Received response with unexpected content type '%s'.".formatted(contentType), null)));
        }

        // Legacy error handling
        return handleLegacyErrorResponse(clientResponse);
    }

    /**
     * Map a non-2xx response with no {@code application/problem+json} body onto the connector
     * exception its status means.
     *
     * <p>Each branch below reads the body as a {@code String} for the exception message and needs
     * {@code defaultIfEmpty} to do it: {@code bodyToMono(String.class)} completes <em>empty</em> for a
     * zero-length body, and an empty source never runs {@code flatMap}, so without the default the
     * whole filter completes empty and no exception is ever raised. The error status then vanishes —
     * {@code requireResponse}/{@code requireBody} see a null entity and throw
     * {@link IllegalStateException}, which {@code processRequest} rethrows unmapped, past every
     * caller that catches {@code ConnectorException} and past the discovery client's {@code cancel}
     * not-tracked handling. A bodiless error status is an ordinary shape, not a curiosity: a Go
     * connector's {@code w.WriteHeader(404)} sends no body at all, and discovery's {@code cancel} is
     * declared bodiless even on success.
     */
    private static Mono<ClientResponse> handleLegacyErrorResponse(ClientResponse clientResponse) {
        if (HttpStatus.UNPROCESSABLE_ENTITY.equals(clientResponse.statusCode())) {
            return clientResponse.bodyToMono(ERROR_LIST_TYPE_REF).flatMap(body ->
                    Mono.error(new ValidationException(body.stream()
                                    .map(ValidationError::create)
                                    .toList()
                            )
                    )
            );
        }
        if (HttpStatus.NOT_FOUND.equals(clientResponse.statusCode())) {
            return clientResponse.bodyToMono(String.class)
                    .defaultIfEmpty("")
                    .flatMap(body -> Mono.error(new ConnectorEntityNotFoundException(body)));
        }
        if (clientResponse.statusCode().is4xxClientError()) {
            return clientResponse.bodyToMono(String.class)
                    .defaultIfEmpty("")
                    .flatMap(body -> Mono.error(new ConnectorClientException(body, HttpStatus.valueOf(clientResponse.statusCode().value()))));
        }
        if (clientResponse.statusCode().is5xxServerError()) {
            return clientResponse.bodyToMono(String.class)
                    .defaultIfEmpty("")
                    .flatMap(body -> Mono.error(new ConnectorServerException(body, HttpStatus.valueOf(clientResponse.statusCode().value()))));
        }
        return Mono.just(clientResponse);
    }

    private static Mono<ClientResponse> handleProblemDetailResponse(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(ProblemDetailExtended.class)
                .flatMap(problemDetail -> Mono.error(new ConnectorProblemException(problemDetail)));
    }
}
