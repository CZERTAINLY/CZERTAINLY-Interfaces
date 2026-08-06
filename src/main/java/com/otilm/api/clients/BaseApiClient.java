package com.otilm.api.clients;

import com.otilm.api.exception.*;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    private static final int CODEC_MAX_IN_MEMORY = 16 * 1024 * 1024;

    // Pool hygiene (not deployment-specific): evict idle and age-cap connections so a server-closed
    // keep-alive is not reused (PrematureCloseException).
    private static final Duration POOL_MAX_IDLE = Duration.ofSeconds(30);
    private static final Duration POOL_MAX_LIFE = Duration.ofMinutes(5);
    private static final Duration POOL_EVICT_INTERVAL = Duration.ofSeconds(30);
    private static final Duration POOL_DISPOSE_INTERVAL = Duration.ofSeconds(120);
    private static final Duration POOL_DISPOSE_AFTER = Duration.ofSeconds(300);

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
        return buildWebClient(baseHttpClient);
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

    private static WebClient buildWebClient(HttpClient httpClient) {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(CODEC_MAX_IN_MEMORY))
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
            } else if (isJsonTypeResolutionFailure(unwrapped)) {
                // Must precede the IOException branch: JsonProcessingException extends IOException
                // and would otherwise be misclassified as a transport failure. The thrown message
                // must stay generic — Jackson's own message echoes connector response fragments,
                // and ValidationException is part of the platform's outward error surface.
                logger.debug("Connector {} response failed type resolution ({}): {}",
                        connector.getName(), unwrapped.getClass().getSimpleName(), unwrapped.getMessage());
                ValidationException validationException = new ValidationException(ValidationError.create(
                        "Connector %s response could not be parsed against the expected type"
                                .formatted(connector.getName())));
                validationException.initCause(unwrapped);
                throw validationException;
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
     * The pool pending-limit exception is a plain RuntimeException (unlike PoolAcquireTimeoutException,
     * a {@link TimeoutException} matched above). Match by name to avoid depending on Reactor-Netty's
     * shaded internal pool type.
     */
    @SuppressWarnings("java:S1872") // intentional name match — instanceof would couple to the shaded type
    private static boolean isPoolAcquireExhausted(Throwable t) {
        return "PoolAcquirePendingLimitException".equals(t.getClass().getSimpleName());
    }

    /**
     * True for a bare Jackson {@link JsonProcessingException} — covering {@code MismatchedInputException},
     * {@code InvalidTypeIdException}, {@code ValueInstantiationException} and {@code InvalidFormatException}
     * — or one wrapped as the cause of another exception. Spring WebFlux's {@code Jackson2JsonDecoder}
     * wraps body-decode failures in {@code org.springframework.core.codec.DecodingException}, so both
     * forms have to match.
     */
    private static boolean isJsonTypeResolutionFailure(Throwable t) {
        return t instanceof JsonProcessingException || t.getCause() instanceof JsonProcessingException;
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
            // Attempt to parse legacy error format
            return clientResponse.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new ConnectorCommunicationException("Received response with unexpected content type '%s'.".formatted(contentType), null)));
        }

        // Legacy error handling
        return handleLegacyErrorResponse(clientResponse);
    }

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
                    .flatMap(body -> Mono.error(new ConnectorEntityNotFoundException(body)));
        }
        if (clientResponse.statusCode().is4xxClientError()) {
            return clientResponse.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new ConnectorClientException(body, HttpStatus.valueOf(clientResponse.statusCode().value()))));
        }
        if (clientResponse.statusCode().is5xxServerError()) {
            return clientResponse.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new ConnectorServerException(body, HttpStatus.valueOf(clientResponse.statusCode().value()))));
        }
        return Mono.just(clientResponse);
    }

    private static Mono<ClientResponse> handleProblemDetailResponse(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(ProblemDetailExtended.class)
                .flatMap(problemDetail -> Mono.error(new ConnectorProblemException(problemDetail)));
    }
}
