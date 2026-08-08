package com.otilm.api.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.otilm.api.model.client.attribute.ResponseAttribute;
import com.otilm.api.model.client.attribute.ResponseAttributeV2;
import com.otilm.api.model.common.attribute.common.content.AttributeContentType;
import com.otilm.api.model.common.attribute.common.content.data.FileAttributeContentData;
import com.otilm.api.model.common.attribute.common.content.data.SecretAttributeContentData;
import com.otilm.api.model.common.attribute.v2.content.FileAttributeContentV2;
import com.otilm.api.model.common.attribute.v2.content.SecretAttributeContentV2;
import com.otilm.api.model.common.attribute.v2.content.StringAttributeContentV2;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.otilm.api.exception.ConnectorClientException;
import com.otilm.api.exception.ConnectorCommunicationException;
import com.otilm.api.exception.ConnectorServerException;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.core.connector.AuthType;
import com.otilm.api.model.core.connector.ConnectorStatus;
import com.otilm.api.model.core.proxy.ProxyDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.codec.DecodingException;
import org.springframework.core.io.buffer.DataBufferLimitException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.*;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class BaseApiClientTest {

    /** Read from {@link ClientTuning} so a literal copy cannot drift from the record's default. */
    private static final int DEFAULT_MAX_IN_MEMORY = ClientTuning.defaults().maxInMemorySize();

    private WireMockServer mockServer;
    private TestApiClient client;

    @BeforeEach
    void setUp() {
        mockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
        mockServer.start();
        WireMock.configureFor("localhost", mockServer.port());
        mockServer.stubFor(get(anyUrl()).willReturn(aResponse().withStatus(200)));

        client = new TestApiClient(BaseApiClient.prepareWebClient());
    }

    @AfterEach
    void tearDown() {
        // Reset the static tuning/cache regardless of mockServer.stop() outcome, so state never leaks
        // into the next test (which would make the tuned-timeout cases order-dependent).
        try {
            mockServer.stop();
        } finally {
            BaseApiClient.resetConnectorClientForTest();
        }
    }

    /**
     * {@code bodyToMono} completes empty for a zero-length body, so without {@code defaultIfEmpty} the
     * {@code flatMap} never runs and the failure escapes as an unmapped
     * {@code IllegalStateException}. Realistic from a reverse proxy that sets {@code text/html} and
     * sends no body.
     */
    @Test
    void unexpectedContentTypeWithEmptyBodyStillMapsToAConnectorException() {
        mockServer.stubFor(get(urlEqualTo("/html-empty")).willReturn(aResponse()
                .withStatus(502)
                .withHeader("Content-Type", "text/html")));
        TestConnectorInfo connector = new TestConnectorInfo("http://localhost:" + mockServer.port(), AuthType.NONE, List.of());

        Assertions.assertThrows(ConnectorCommunicationException.class, () ->
                BaseApiClient.processRequest(r -> r
                                .uri("http://localhost:" + mockServer.port() + "/html-empty")
                                .retrieve()
                                .toBodilessEntity()
                                .block(),
                        client.prepareRequest(HttpMethod.GET, connector, false),
                        connector));
    }

    @Test
    void prepareRequest_basicAuth_sendsCorrectAuthorizationHeader() {
        List<ResponseAttribute> authAttributes = List.of(
                responseAttribute("username", AttributeContentType.STRING, new StringAttributeContentV2("admin")),
                responseAttribute("password", AttributeContentType.SECRET, new SecretAttributeContentV2(null, new SecretAttributeContentData("secret123")))
        );

        TestConnectorInfo connector = new TestConnectorInfo("http://localhost:" + mockServer.port(), AuthType.BASIC, authAttributes);

        assertDoesNotThrow(() ->
                client.prepareRequest(HttpMethod.GET, connector, false)
                        .uri("http://localhost:" + mockServer.port() + "/test")
                        .retrieve()
                        .toBodilessEntity()
                        .block()
        );

        String expectedHeader = "Basic " + Base64.getEncoder().encodeToString("admin:secret123".getBytes(StandardCharsets.UTF_8));
        mockServer.verify(getRequestedFor(urlEqualTo("/test"))
                .withHeader("Authorization", equalTo(expectedHeader)));
    }

    @Test
    void prepareRequest_apiKeyAuth_sendsCorrectApiKeyHeader() {
        List<ResponseAttribute> authAttributes = List.of(
                responseAttribute("apiKeyHeader", AttributeContentType.STRING, new StringAttributeContentV2("X-API-KEY")),
                responseAttribute("apiKey", AttributeContentType.SECRET, new SecretAttributeContentV2(null, new SecretAttributeContentData("my-api-key")))
        );

        TestConnectorInfo connector = new TestConnectorInfo("http://localhost:" + mockServer.port(), AuthType.API_KEY, authAttributes);

        assertDoesNotThrow(() ->
                client.prepareRequest(HttpMethod.GET, connector, false)
                        .uri("http://localhost:" + mockServer.port() + "/test")
                        .retrieve()
                        .toBodilessEntity()
                        .block()
        );

        mockServer.verify(getRequestedFor(urlEqualTo("/test"))
                .withHeader("X-API-KEY", equalTo("my-api-key")));
    }

    /**
     * Starts an in-memory SSLServerSocket with needClientAuth=true and a truststore containing only
     * the generated client cert. The request succeeds only if BaseApiClient wires the SSL context
     * (including the client keystore) into the WebClient — the bug this test guards was that the
     * mutated WebClient was discarded, so no client cert was ever sent.
     */
    @Test
    void prepareRequest_certificateAuth_clientCertPresentedDuringMtls() throws Exception {
        KeyPair serverKeyPair = generateKeyPair();
        X509Certificate serverCert = generateCert(serverKeyPair, "localhost");
        KeyPair clientKeyPair = generateKeyPair();
        X509Certificate clientCert = generateCert(clientKeyPair, "client");

        MtlsServer server = startMtlsServer(serverKeyPair, serverCert, clientCert);
        try {
            String clientKsBase64 = Base64.getEncoder().encodeToString(
                    buildPkcs12(clientKeyPair, clientCert, "client", "clientPass"));
            String clientTsBase64 = Base64.getEncoder().encodeToString(
                    buildTrustStore(serverCert, "server", "clientTrustPass"));

            FileAttributeContentData ksData = new FileAttributeContentData();
            ksData.setContent(clientKsBase64);
            ksData.setFileName("client.p12");

            FileAttributeContentData tsData = new FileAttributeContentData();
            tsData.setContent(clientTsBase64);
            tsData.setFileName("trust.p12");

            List<ResponseAttribute> authAttributes = List.of(
                    responseAttribute("keyStoreType", AttributeContentType.STRING, new StringAttributeContentV2("PKCS12")),
                    responseAttribute("keyStore", AttributeContentType.FILE, new FileAttributeContentV2(null, ksData)),
                    responseAttribute("keyStorePassword", AttributeContentType.SECRET, new SecretAttributeContentV2(null, new SecretAttributeContentData("clientPass"))),
                    responseAttribute("trustStoreType", AttributeContentType.STRING, new StringAttributeContentV2("PKCS12")),
                    responseAttribute("trustStore", AttributeContentType.FILE, new FileAttributeContentV2(null, tsData)),
                    responseAttribute("trustStorePassword", AttributeContentType.SECRET, new SecretAttributeContentV2(null, new SecretAttributeContentData("clientTrustPass")))
            );

            String url = "https://localhost:" + server.port();
            TestConnectorInfo connector = new TestConnectorInfo(url, AuthType.CERTIFICATE, authAttributes);

            assertDoesNotThrow(() ->
                    client.prepareRequest(HttpMethod.GET, connector, false)
                            .uri(url + "/test")
                            .retrieve()
                            .toBodilessEntity()
                            .block()
            );

            // Would have thrown on handshake failure if the client cert was not wired in
            server.future().get(5, TimeUnit.SECONDS);
        } finally {
            server.close();
        }
    }

    @Test
    void prepareRequest_certificateAuth_failsWithoutClientKeystore() throws Exception {
        KeyPair serverKeyPair = generateKeyPair();
        X509Certificate serverCert = generateCert(serverKeyPair, "localhost");
        KeyPair clientKeyPair = generateKeyPair();
        X509Certificate clientCert = generateCert(clientKeyPair, "client");

        MtlsServer server = startMtlsServer(serverKeyPair, serverCert, clientCert);
        try {
            // Only truststore — no keystore, so no client cert is sent
            String clientTsBase64 = Base64.getEncoder().encodeToString(
                    buildTrustStore(serverCert, "server", "clientTrustPass"));

            FileAttributeContentData tsData = new FileAttributeContentData();
            tsData.setContent(clientTsBase64);
            tsData.setFileName("trust.p12");

            List<ResponseAttribute> authAttributes = List.of(
                    responseAttribute("trustStoreType", AttributeContentType.STRING, new StringAttributeContentV2("PKCS12")),
                    responseAttribute("trustStore", AttributeContentType.FILE, new FileAttributeContentV2(null, tsData)),
                    responseAttribute("trustStorePassword", AttributeContentType.SECRET, new SecretAttributeContentV2(null, new SecretAttributeContentData("clientTrustPass")))
            );

            String url = "https://localhost:" + server.port();
            TestConnectorInfo connector = new TestConnectorInfo(url, AuthType.CERTIFICATE, authAttributes);

            Assertions.assertThrows(Exception.class, () ->
                    client.prepareRequest(HttpMethod.GET, connector, false)
                            .uri(url + "/test")
                            .retrieve()
                            .toBodilessEntity()
                            .block()
            );
        } finally {
            server.close();
        }
    }

    private MtlsServer startMtlsServer(KeyPair serverKeyPair, X509Certificate serverCert, X509Certificate trustedClientCert) throws Exception {
        KeyManagerFactory serverKmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        serverKmf.init(loadKeyStore(buildPkcs12(serverKeyPair, serverCert, "server", "serverPass"), "serverPass"), "serverPass".toCharArray());
        TrustManagerFactory serverTmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        serverTmf.init(loadKeyStore(buildTrustStore(trustedClientCert, "client", "serverTrustPass"), "serverTrustPass"));
        SSLContext serverSslCtx = SSLContext.getInstance("TLS");
        serverSslCtx.init(serverKmf.getKeyManagers(), serverTmf.getTrustManagers(), null);

        SSLServerSocket serverSocket = (SSLServerSocket) serverSslCtx.getServerSocketFactory().createServerSocket(0);
        serverSocket.setNeedClientAuth(true);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            try (SSLSocket socket = (SSLSocket) serverSocket.accept()) {
                socket.setSoTimeout(5000);
                byte[] buf = new byte[4096];
                socket.getInputStream().read(buf);
                socket.getOutputStream().write(
                        "HTTP/1.1 200 OK\r\nContent-Length: 0\r\nConnection: close\r\n\r\n".getBytes());
                socket.getOutputStream().flush();
            }
            return null;
        });

        return new MtlsServer(serverSocket, executor, future);
    }

    private record MtlsServer(SSLServerSocket socket, ExecutorService executor, Future<?> future) {
        int port() { return socket.getLocalPort(); }
        void close() throws Exception {
            executor.shutdownNow();
            socket.close();
        }
    }

    @Test
    void prepareRequest_basicAuth_missingCredentials_throwsIllegalArgumentException() {
        TestConnectorInfo connector = new TestConnectorInfo("http://localhost:" + mockServer.port(), AuthType.BASIC, List.of());

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                client.prepareRequest(HttpMethod.GET, connector, false));
    }

    @Test
    void prepareRequest_apiKeyAuth_missingCredentials_throwsIllegalArgumentException() {
        TestConnectorInfo connector = new TestConnectorInfo("http://localhost:" + mockServer.port(), AuthType.API_KEY, List.of());

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                client.prepareRequest(HttpMethod.GET, connector, false));
    }

    @Test
    void certificateWebClient_sameConnector_reusesCachedClientWithoutRebuildingSslContext() throws Exception {
        List<ResponseAttribute> authAttributes = certAuthAttributes("clientPass", "clientTrustPass");
        TestConnectorInfo connector = new TestConnectorInfo("https://localhost:9999", AuthType.CERTIFICATE, authAttributes);

        WebClient first = client.certificateWebClient(connector);
        WebClient second = client.certificateWebClient(connector);

        // Same instance proves the SslContext (and its WebClient) was cached, not rebuilt per request.
        Assertions.assertSame(first, second);
    }

    @Test
    void certificateWebClient_rotatedCredentials_rebuildsClient() throws Exception {
        List<ResponseAttribute> original = certAuthAttributes("clientPass", "clientTrustPass");
        TestConnectorInfo connector = new TestConnectorInfo("https://localhost:9999", AuthType.CERTIFICATE, original);
        WebClient first = client.certificateWebClient(connector);

        // Same connector UUID, different keystore/truststore material -> cache entry must be invalidated.
        List<ResponseAttribute> rotated = certAuthAttributes("clientPass", "clientTrustPass");
        TestConnectorInfo rotatedConnector = new TestConnectorInfo("https://localhost:9999", AuthType.CERTIFICATE, rotated);
        WebClient second = client.certificateWebClient(rotatedConnector);

        Assertions.assertNotSame(first, second);
    }

    @Test
    void prepareRequest_slowResponse_failsFastWithinResponseTimeout() {
        BaseApiClient.resetConnectorClientForTest(); // claim write-once tuning for this test
        WebClient tuned = BaseApiClient.prepareWebClient(
                new ClientTuning(Duration.ofSeconds(1), Duration.ofMillis(500), 5, Duration.ofSeconds(1), DEFAULT_MAX_IN_MEMORY));
        TestApiClient tunedClient = new TestApiClient(tuned);
        mockServer.stubFor(get(urlEqualTo("/slow")).willReturn(aResponse().withStatus(200).withFixedDelay(5000)));
        TestConnectorInfo connector = new TestConnectorInfo("http://localhost:" + mockServer.port(), AuthType.NONE, List.of());

        long startMs = System.currentTimeMillis();
        Throwable thrown = Assertions.assertThrows(Exception.class, () ->
                tunedClient.prepareRequest(HttpMethod.GET, connector, false)
                        .uri("http://localhost:" + mockServer.port() + "/slow")
                        .retrieve()
                        .toBodilessEntity()
                        .block());
        long elapsedMs = System.currentTimeMillis() - startMs;

        // Proves fail-fast: the 500ms response timeout must trip before the server's 5000ms delay
        // would return. Bound kept comfortably under 5000ms (not tight to 500ms) so a GC pause or a
        // saturated CI runner doesn't flake it; hasTimeoutCause below confirms the reason.
        Assertions.assertTrue(elapsedMs < 4500, "expected fail-fast under the 500ms response timeout, took " + elapsedMs + "ms");
        // Ensure it failed for the intended reason (a timeout), not a URI/connect error that merely happened fast.
        Assertions.assertTrue(hasTimeoutCause(thrown), "expected a response/read timeout, got: " + thrown);
    }

    private static boolean hasTimeoutCause(Throwable thrown) {
        for (Throwable t = thrown; t != null; t = t.getCause()) {
            if (t instanceof java.util.concurrent.TimeoutException
                    || t instanceof io.netty.handler.timeout.TimeoutException
                    || t.getClass().getSimpleName().toLowerCase(Locale.ROOT).contains("timeout")) {
                return true;
            }
            if (t == t.getCause()) {
                break;
            }
        }
        return false;
    }

    @Test
    void processRequest_responseTimeout_mappedToConnectorCommunicationException() {
        BaseApiClient.resetConnectorClientForTest(); // claim write-once tuning for this test
        WebClient tuned = BaseApiClient.prepareWebClient(
                new ClientTuning(Duration.ofSeconds(1), Duration.ofMillis(500), 5, Duration.ofSeconds(1), DEFAULT_MAX_IN_MEMORY));
        TestApiClient tunedClient = new TestApiClient(tuned);
        mockServer.stubFor(get(urlEqualTo("/slow")).willReturn(aResponse().withStatus(200).withFixedDelay(5000)));
        TestConnectorInfo connector = new TestConnectorInfo("http://localhost:" + mockServer.port(), AuthType.NONE, List.of());

        Assertions.assertThrows(ConnectorCommunicationException.class, () ->
                BaseApiClient.processRequest(
                        req -> tunedClient.prepareRequest(HttpMethod.GET, connector, false)
                                .uri("http://localhost:" + mockServer.port() + "/slow")
                                .retrieve()
                                .toBodilessEntity()
                                .block(),
                        null,
                        connector));
    }

    /**
     * A response exceeding the codec's {@code maxInMemorySize} must be classified as a connector fault
     * ({@link ConnectorServerException}), not escape unmapped as the
     * {@link org.springframework.web.reactive.function.client.WebClientResponseException} that
     * {@code retrieve()} wraps it in. Decoding to {@code String} rather than a DTO keeps the stubbed
     * body trivially valid at any size, isolating the size gate from parse validity.
     *
     * <p>The mapped status must be the one the connector actually sent ({@code 200} here), never a
     * synthesized {@code 413}: Core's {@code ExceptionHandlingAdvice} appends "Original response code
     * &lt;status&gt;" to the operator-facing error verbatim.
     */
    @Test
    void processRequest_oversizedResponse_mappedToConnectorServerException() {
        BaseApiClient.resetConnectorClientForTest(); // claim write-once tuning for this test
        int smallCap = 1024;
        WebClient tuned = BaseApiClient.prepareWebClient(
                new ClientTuning(Duration.ofSeconds(3), Duration.ofSeconds(10), 5, Duration.ofSeconds(1), smallCap));
        TestApiClient tunedClient = new TestApiClient(tuned);
        mockServer.stubFor(get(urlEqualTo("/oversized")).willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "text/plain")
                .withBody("a".repeat(smallCap * 4))));
        TestConnectorInfo connector = new TestConnectorInfo("http://localhost:" + mockServer.port(), AuthType.NONE, List.of());

        ConnectorServerException ex = Assertions.assertThrows(ConnectorServerException.class, () ->
                BaseApiClient.processRequest(
                        req -> tunedClient.prepareRequest(HttpMethod.GET, connector, false)
                                .uri("http://localhost:" + mockServer.port() + "/oversized")
                                .retrieve()
                                .toEntity(String.class)
                                .block(),
                        null,
                        connector));

        // The stub answered 200; that is what must be reported, and it must NOT be PAYLOAD_TOO_LARGE.
        Assertions.assertEquals(HttpStatus.OK, ex.getHttpStatus());
        Assertions.assertEquals(connector, ex.getConnector());
    }

    /**
     * A 422 and a problem+json response are read as a typed body rather than a string, so each needed
     * its own empty-body fallback: an empty source never runs {@code flatMap}, and the status would
     * otherwise vanish into an unmapped {@code IllegalStateException} instead of the mapped
     * {@code ConnectorException} the client promises.
     */
    @Test
    void bodilessValidationResponseStillMapsToValidationException() {
        mockServer.stubFor(get(urlEqualTo("/empty-422")).willReturn(aResponse().withStatus(422)));
        TestConnectorInfo connector = new TestConnectorInfo("http://localhost:" + mockServer.port(), AuthType.NONE, List.of());

        Assertions.assertThrows(ValidationException.class, () ->
                BaseApiClient.processRequest(r -> r
                                .uri("http://localhost:" + mockServer.port() + "/empty-422")
                                .retrieve().toBodilessEntity().block(),
                        client.prepareRequest(HttpMethod.GET, connector, false), connector));
    }

    @Test
    void bodilessProblemJsonResponseMapsByStatusInsteadOfEscaping() {
        mockServer.stubFor(get(urlEqualTo("/empty-problem")).willReturn(aResponse()
                .withStatus(502)
                .withHeader("Content-Type", "application/problem+json")));
        TestConnectorInfo connector = new TestConnectorInfo("http://localhost:" + mockServer.port(), AuthType.NONE, List.of());

        ConnectorServerException ex = Assertions.assertThrows(ConnectorServerException.class, () ->
                BaseApiClient.processRequest(r -> r
                                .uri("http://localhost:" + mockServer.port() + "/empty-problem")
                                .retrieve().toBodilessEntity().block(),
                        client.prepareRequest(HttpMethod.GET, connector, false), connector));

        Assertions.assertEquals(HttpStatus.BAD_GATEWAY, ex.getHttpStatus(),
                "with no problem body there is no ErrorCode to carry, so the status alone must classify it");
    }

    /**
     * The same rule for the communication failure. Its handler maps to 503 and copies the message the
     * same way, so a URL here reached callers identically — this one predated the discovery work, which
     * is the only reason it survived two rounds of cleaning the neighbouring messages.
     */
    @Test
    void communicationFailureMessageDoesNotCarryTheConnectorUrl() {
        String url = "https://svc-user:s3cret@internal-connector.svc.cluster.local:9999/api";
        TestConnectorInfo connector = new TestConnectorInfo(url, AuthType.NONE, List.of());

        ConnectorCommunicationException ex = Assertions.assertThrows(ConnectorCommunicationException.class, () ->
                BaseApiClient.processRequest(req -> {
                    throw reactor.core.Exceptions.propagate(new java.net.ConnectException("refused"));
                }, null, connector));

        Assertions.assertFalse(ex.getMessage().contains(url), "the outward message must not carry the URL");
        Assertions.assertFalse(ex.getMessage().contains("s3cret"), "credentials in the URL must never reach a caller");
        Assertions.assertEquals(connector, ex.getConnector());
    }

    /**
     * Core's {@code handleConnectorServerException} copies the exception message verbatim into its 502
     * response body, and already appends the connector's name and uuid itself. So the connector URL in
     * the message bought nothing for attribution while exposing internal topology — and any credentials
     * carried in user-info or a query string — to whoever called the platform API.
     */
    @Test
    void outwardConnectorFailureMessagesDoNotCarryTheConnectorUrl() {
        String url = "https://svc-user:s3cret@internal-connector.svc.cluster.local:8443/api";
        TestConnectorInfo connector = new TestConnectorInfo(url, AuthType.NONE, List.of());
        JsonProcessingException jackson = new com.fasterxml.jackson.databind.exc.MismatchedInputException(
                null, "bad shape") {
        };

        ConnectorServerException ex = Assertions.assertThrows(ConnectorServerException.class, () ->
                BaseApiClient.processRequest(req -> {
                    throw reactor.core.Exceptions.propagate(jackson);
                }, null, connector));

        Assertions.assertFalse(ex.getMessage().contains(url), "the outward message must not carry the URL");
        Assertions.assertFalse(ex.getMessage().contains("s3cret"), "credentials in the URL must never reach a caller");
        Assertions.assertEquals(connector, ex.getConnector(), "attribution stays on the exception, not in the message");
    }

    /**
     * A connector whose body does not match the expected type is a connector fault, not a transport
     * failure and not the caller's mistake. It must report 502: Core serves 422 for a caller's own
     * invalid input, so 422 here would blame the user and invert the retry signal.
     *
     * <p>The message is the other half. Jackson's own message quotes fragments of the response body,
     * and in discovery that can carry key material, so the outward exception message must stay generic
     * while the cause keeps the detail.
     */
    @Test
    void processRequest_unparseableResponse_reportsBadGatewayWithoutEchoingTheBody() {
        String secret = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A-not-for-logs";
        JsonProcessingException jackson = new com.fasterxml.jackson.databind.exc.MismatchedInputException(
                null, "Cannot deserialize value: " + secret) {
        };
        TestConnectorInfo connector = new TestConnectorInfo("http://localhost:1", AuthType.NONE, List.of());

        // Thrown through Reactor's propagate so processRequest's Exceptions.unwrap yields the Jackson
        // exception itself. That matters: unwrap strips only Reactor wrappers, so if the fixture nested
        // the Jackson failure inside a DecodingException, the message being interpolated would be the
        // wrapper's ("decode failed") and this test could not detect the leak it exists to catch.
        ConnectorServerException ex = Assertions.assertThrows(ConnectorServerException.class, () ->
                BaseApiClient.processRequest(req -> {
                    throw reactor.core.Exceptions.propagate(jackson);
                }, null, connector));

        Assertions.assertEquals(HttpStatus.BAD_GATEWAY, ex.getHttpStatus(),
                "an unparseable connector response is a 502 upstream fault, never a 422 blamed on the caller");
        Assertions.assertEquals(connector, ex.getConnector(), "the fault must name the connector that caused it");
        Assertions.assertFalse(ex.getMessage().contains(secret),
                "the outward message must not echo the connector's response body; Jackson's message quotes it");
        Assertions.assertSame(jackson, ex.getCause(), "the cause must be retained for diagnostics");
    }

    /**
     * The wrapping is not always one layer deep: {@code retrieve()} can surface a
     * {@code WebClientResponseException} around the {@code DecodingException} the Jackson failure
     * arrives in. A one-level check misses that, and the miss reaches {@code processRequest}'s
     * catch-all, which logs the exception message — which quotes the connector's response body.
     */
    @Test
    void processRequest_deeplyWrappedJacksonFailure_isStillClassifiedAndDoesNotReachTheCatchAll() {
        String secret = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A-buried-two-levels-down";
        JsonProcessingException jackson = new com.fasterxml.jackson.databind.exc.MismatchedInputException(
                null, "Cannot deserialize value: " + secret) {
        };
        Throwable twoLevels = new DecodingException("outer", new DecodingException("inner", jackson));
        TestConnectorInfo connector = new TestConnectorInfo("http://localhost:1", AuthType.NONE, List.of());

        ConnectorServerException ex = Assertions.assertThrows(ConnectorServerException.class, () ->
                BaseApiClient.processRequest(req -> {
                    throw reactor.core.Exceptions.propagate(twoLevels);
                }, null, connector));

        Assertions.assertEquals(HttpStatus.BAD_GATEWAY, ex.getHttpStatus());
        Assertions.assertFalse(ex.getMessage().contains(secret),
                "the outward message must not echo the body, however deeply the failure was wrapped");
    }

    /**
     * The branch order matters: {@code JsonProcessingException} extends {@code IOException}, so a bare
     * Jackson failure reaching the transport branch first would be reported as a communication failure
     * (503, retryable) rather than a contract violation (502).
     */
    @Test
    void processRequest_bareJacksonFailure_isNotMisreadAsATransportFailure() {
        TestConnectorInfo connector = new TestConnectorInfo("http://localhost:1", AuthType.NONE, List.of());
        JsonProcessingException bare = new com.fasterxml.jackson.core.JsonParseException(null, "bad token") {
        };

        ConnectorServerException ex = Assertions.assertThrows(ConnectorServerException.class, () ->
                BaseApiClient.processRequest(req -> {
                    throw new RuntimeException(bare);
                }, null, connector));

        Assertions.assertEquals(HttpStatus.BAD_GATEWAY, ex.getHttpStatus());
    }

    /**
     * A read-limit breach reaching {@code processRequest} without a
     * {@link org.springframework.web.reactive.function.client.WebClientResponseException} around it
     * carries no upstream status to report, so {@link HttpStatus#BAD_GATEWAY} is the answer — again
     * not a synthesized {@code 413}.
     */
    @Test
    void processRequest_bareOversizedResponse_reportsBadGatewayRatherThanASynthesizedStatus() {
        TestConnectorInfo connector = new TestConnectorInfo("http://localhost:" + mockServer.port(), AuthType.NONE, List.of());

        ConnectorServerException ex = Assertions.assertThrows(ConnectorServerException.class, () ->
                BaseApiClient.processRequest(req -> {
                    throw new DataBufferLimitException("Exceeded limit on max bytes to buffer : 1024");
                }, null, connector));

        Assertions.assertEquals(HttpStatus.BAD_GATEWAY, ex.getHttpStatus());
        Assertions.assertEquals(connector, ex.getConnector());
    }

    /**
     * The read-limit breach need not sit at the top of the chain or one level under it: Spring's
     * {@code Jackson2JsonDecoder} raises a {@link DecodingException} around a decode failure, so a
     * breach can arrive two or more levels down. Matching only the bare form and one level of wrapping
     * lets that escape to {@code processRequest}'s catch-all and surface as a Spring-internal type.
     */
    @Test
    void processRequest_deeplyWrappedOversizedResponse_stillMappedToConnectorServerException() {
        TestConnectorInfo connector = new TestConnectorInfo("http://localhost:" + mockServer.port(), AuthType.NONE, List.of());
        DataBufferLimitException limitBreach = new DataBufferLimitException("Exceeded limit on max bytes to buffer : 1024");
        DecodingException decodingFailure = new DecodingException("Could not read document", limitBreach);
        IllegalStateException outer = new IllegalStateException("wrapped once more", decodingFailure);

        ConnectorServerException ex = Assertions.assertThrows(ConnectorServerException.class, () ->
                BaseApiClient.processRequest(req -> {
                    throw outer;
                }, null, connector));

        Assertions.assertEquals(connector, ex.getConnector());
        Assertions.assertSame(outer, ex.getCause());
    }

    /**
     * The cause-chain walk runs inside an exception handler, so it must terminate on a cyclic chain.
     * The two exceptions below cause each other, which the walk's cheap self-cause check cannot
     * detect: only its depth bound stops it. Preemptive timeout because the failure mode guarded
     * against is a hang, which no assertion would ever reach.
     */
    @Test
    void processRequest_cyclicCauseChain_terminatesInsteadOfSpinning() {
        TestConnectorInfo connector = new TestConnectorInfo("http://localhost:" + mockServer.port(), AuthType.NONE, List.of());
        CyclicCauseException first = new CyclicCauseException("cycle head");
        CyclicCauseException second = new CyclicCauseException("cycle tail");
        first.causedBy(second);
        second.causedBy(first);

        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(10), () ->
                Assertions.assertThrows(ValidationException.class, () ->
                        BaseApiClient.processRequest(req -> {
                            throw first;
                        }, null, connector)));
    }

    /**
     * A cause cycle of length two. Deliberately not a self-cause: {@code a -> b -> a} is invisible to
     * an {@code exception == exception.getCause()} check, so only a bounded walk survives it.
     * {@link ValidationException} is the carrier so {@code processRequest} logs the message alone —
     * logging the throwable would hand the cycle to the logging framework's stack-trace renderer,
     * testing that instead of this class.
     */
    private static class CyclicCauseException extends ValidationException {
        private transient Throwable partner;

        CyclicCauseException(String message) {
            super(message);
        }

        void causedBy(Throwable cause) {
            this.partner = cause;
        }

        @Override
        public synchronized Throwable getCause() {
            return partner;
        }
    }

    /**
     * A connector answering an error status with a zero-length body must still produce the mapped
     * {@link ConnectorClientException}. {@code bodyToMono(String.class)} completes empty for a bodiless
     * response and an empty source never runs {@code flatMap}, so without a default the response filter
     * completes empty, {@code requireResponse} sees a null entity, and the status is lost to an unmapped
     * {@link IllegalStateException}. A Go connector's {@code w.WriteHeader(400)} sends this shape.
     */
    @Test
    void processRequest_bodilessClientError_stillMappedToConnectorClientException() {
        mockServer.stubFor(get(urlEqualTo("/bodiless-400")).willReturn(aResponse().withStatus(400)));
        TestConnectorInfo connector = new TestConnectorInfo("http://localhost:" + mockServer.port(), AuthType.NONE, List.of());

        ConnectorClientException ex = Assertions.assertThrows(ConnectorClientException.class, () ->
                BaseApiClient.processRequest(
                        req -> BaseApiClient.requireResponse(client.prepareRequest(HttpMethod.GET, connector, false)
                                .uri("http://localhost:" + mockServer.port() + "/bodiless-400")
                                .retrieve()
                                .toEntity(String.class), "bodiless 400"),
                        null,
                        connector));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, ex.getHttpStatus());
        Assertions.assertEquals(connector, ex.getConnector());
    }

    /** The 5xx half of the bodiless-error mapping; see the 4xx case above for why it is needed. */
    @Test
    void processRequest_bodilessServerError_stillMappedToConnectorServerException() {
        mockServer.stubFor(get(urlEqualTo("/bodiless-500")).willReturn(aResponse().withStatus(500)));
        TestConnectorInfo connector = new TestConnectorInfo("http://localhost:" + mockServer.port(), AuthType.NONE, List.of());

        ConnectorServerException ex = Assertions.assertThrows(ConnectorServerException.class, () ->
                BaseApiClient.processRequest(
                        req -> BaseApiClient.requireResponse(client.prepareRequest(HttpMethod.GET, connector, false)
                                .uri("http://localhost:" + mockServer.port() + "/bodiless-500")
                                .retrieve()
                                .toEntity(String.class), "bodiless 500"),
                        null,
                        connector));

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getHttpStatus());
        Assertions.assertEquals(connector, ex.getConnector());
    }

    /**
     * A later caller asking for different tuning is warned and ignored, but it still receives a
     * {@code WebClient}, and that client must enforce the tuning that <em>won</em>, not the tuning it
     * asked for. Building it from the caller's own tuning would hand out a client whose read cap
     * disagrees with the one live {@code HttpClient}.
     *
     * <p>The stubbed body is sized between the two caps — over the winning cap, well under the one the
     * second caller asked for — so only the winning cap can fail here.
     */
    @Test
    void prepareWebClient_laterDifferingTuning_stillEnforcesTheWinningCap() {
        BaseApiClient.resetConnectorClientForTest(); // claim write-once tuning for this test
        int winningCap = 1024;
        BaseApiClient.prepareWebClient(
                new ClientTuning(Duration.ofSeconds(3), Duration.ofSeconds(10), 5, Duration.ofSeconds(1), winningCap));
        WebClient later = BaseApiClient.prepareWebClient(
                new ClientTuning(Duration.ofSeconds(3), Duration.ofSeconds(10), 5, Duration.ofSeconds(1), winningCap * 64));
        TestApiClient laterClient = new TestApiClient(later);

        mockServer.stubFor(get(urlEqualTo("/between-caps")).willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "text/plain")
                .withBody("a".repeat(winningCap * 4))));
        TestConnectorInfo connector = new TestConnectorInfo("http://localhost:" + mockServer.port(), AuthType.NONE, List.of());

        Assertions.assertThrows(ConnectorServerException.class, () ->
                BaseApiClient.processRequest(
                        req -> laterClient.prepareRequest(HttpMethod.GET, connector, false)
                                .uri("http://localhost:" + mockServer.port() + "/between-caps")
                                .retrieve()
                                .toEntity(String.class)
                                .block(),
                        null,
                        connector));
    }

    @Test
    void processRequest_poolAcquirePendingLimit_mappedToConnectorCommunicationException() {
        TestConnectorInfo connector = new TestConnectorInfo("http://localhost:" + mockServer.port(), AuthType.NONE, List.of());
        // Reactor-Netty throws this (a plain RuntimeException) when the pending-acquire queue is
        // saturated; a local stand-in with the same simple name exercises the name-based mapping.
        Assertions.assertThrows(ConnectorCommunicationException.class, () ->
                BaseApiClient.processRequest(req -> {
                    throw new PoolAcquirePendingLimitException("pending acquire limit reached");
                }, null, connector));
    }

    private static class PoolAcquirePendingLimitException extends RuntimeException {
        PoolAcquirePendingLimitException(String message) {
            super(message);
        }
    }

    private List<ResponseAttribute> certAuthAttributes(String keyStorePassword, String trustStorePassword) throws Exception {
        KeyPair clientKeyPair = generateKeyPair();
        X509Certificate clientCert = generateCert(clientKeyPair, "client");
        KeyPair serverKeyPair = generateKeyPair();
        X509Certificate serverCert = generateCert(serverKeyPair, "localhost");

        FileAttributeContentData ksData = new FileAttributeContentData();
        ksData.setContent(Base64.getEncoder().encodeToString(buildPkcs12(clientKeyPair, clientCert, "client", keyStorePassword)));
        ksData.setFileName("client.p12");

        FileAttributeContentData tsData = new FileAttributeContentData();
        tsData.setContent(Base64.getEncoder().encodeToString(buildTrustStore(serverCert, "server", trustStorePassword)));
        tsData.setFileName("trust.p12");

        return List.of(
                responseAttribute("keyStoreType", AttributeContentType.STRING, new StringAttributeContentV2("PKCS12")),
                responseAttribute("keyStore", AttributeContentType.FILE, new FileAttributeContentV2(null, ksData)),
                responseAttribute("keyStorePassword", AttributeContentType.SECRET, new SecretAttributeContentV2(null, new SecretAttributeContentData(keyStorePassword))),
                responseAttribute("trustStoreType", AttributeContentType.STRING, new StringAttributeContentV2("PKCS12")),
                responseAttribute("trustStore", AttributeContentType.FILE, new FileAttributeContentV2(null, tsData)),
                responseAttribute("trustStorePassword", AttributeContentType.SECRET, new SecretAttributeContentV2(null, new SecretAttributeContentData(trustStorePassword)))
        );
    }

    private static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(2048);
        return gen.generateKeyPair();
    }

    private static X509Certificate generateCert(KeyPair keyPair, String cn) throws Exception {
        X500Name subject = new X500Name("CN=" + cn);
        Date notBefore = new Date(System.currentTimeMillis() - 1000);
        Date notAfter = new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000);
        ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA").build(keyPair.getPrivate());
        return new JcaX509CertificateConverter().getCertificate(
                new JcaX509v3CertificateBuilder(subject, BigInteger.ONE, notBefore, notAfter, subject, keyPair.getPublic())
                        .build(signer));
    }

    private static byte[] buildPkcs12(KeyPair keyPair, X509Certificate cert, String alias, String password) throws Exception {
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(null, null);
        ks.setKeyEntry(alias, keyPair.getPrivate(), password.toCharArray(), new Certificate[]{cert});
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ks.store(out, password.toCharArray());
        return out.toByteArray();
    }

    private static KeyStore loadKeyStore(byte[] bytes, String password) throws Exception {
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(new ByteArrayInputStream(bytes), password.toCharArray());
        return ks;
    }

    private static byte[] buildTrustStore(X509Certificate cert, String alias, String password) throws Exception {
        KeyStore ts = KeyStore.getInstance("PKCS12");
        ts.load(null, null);
        ts.setCertificateEntry(alias, cert);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ts.store(out, password.toCharArray());
        return out.toByteArray();
    }

    private ResponseAttributeV2 responseAttribute(String name, AttributeContentType contentType, com.otilm.api.model.common.attribute.v2.content.BaseAttributeContentV2<?> content) {
        ResponseAttributeV2 attr = new ResponseAttributeV2();
        attr.setUuid(UUID.randomUUID());
        attr.setName(name);
        attr.setContentType(contentType);
        attr.setContent(List.of(content));
        return attr;
    }

    private static class TestApiClient extends BaseApiClient {
        TestApiClient(WebClient webClient) {
            super(webClient, null);
        }
    }

    private record TestConnectorInfo(
            String url,
            AuthType authType,
            List<ResponseAttribute> authAttributes
    ) implements ApiClientConnectorInfo {
        public String getUuid() { return "test-uuid"; }
        public String getName() { return "test-connector"; }
        public String getUrl() { return url; }
        public ConnectorStatus getStatus() { return ConnectorStatus.CONNECTED; }
        public AuthType getAuthType() { return authType; }
        public List<ResponseAttribute> getAuthAttributes() { return authAttributes; }
        public ProxyDto getProxy() { return null; }
    }
}
