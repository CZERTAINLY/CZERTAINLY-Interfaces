package com.otilm.api.clients.v2;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.otilm.api.clients.BaseApiClient;
import com.otilm.api.exception.ConnectorProblemException;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.common.error.ErrorCode;
import com.otilm.api.model.connector.cryptography.v2.KeyScopedRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.TokenProfileScopedRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.*;
import com.otilm.api.model.core.connector.ConnectorDto;
import com.otilm.api.model.core.connector.ConnectorStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.*;

class CryptographicOperationsApiClientTest {

    private static final String BASE_PATH = "/v2/cryptographyProvider/operations";

    private CryptographicOperationsApiClient client;
    private ConnectorDto connector;
    private WireMockServer server;

    @BeforeEach
    void setUp() {
        client = new CryptographicOperationsApiClient(BaseApiClient.prepareWebClient(), null);
        server = new WireMockServer(options().dynamicPort());
        server.start();
        WireMock.configureFor("localhost", server.port());
        connector = new ConnectorDto();
        connector.setUrl("http://localhost:" + server.port());
        connector.setStatus(ConnectorStatus.CONNECTED);
    }

    @AfterEach
    void tearDown() {
        server.stop();
    }

    @Test
    void signPreservesStructuredConnectorProblem() {
        String problemJson = """
                {
                  "type": "https://docs.otilm.com/problems/common/RATE_LIMIT_EXCEEDED",
                  "title": "Rate limit exceeded",
                  "status": 429,
                  "detail": "Signing quota exhausted",
                  "errorCode": "RATE_LIMIT_EXCEEDED",
                  "timestamp": "2026-07-23T10:00:00Z",
                  "correlationId": "sign-test-correlation",
                  "retryable": true,
                  "retryAfterSeconds": 20
                }
                """;
        server.stubFor(WireMock.post("/v2/cryptographyProvider/operations/sign")
                .willReturn(WireMock.aResponse()
                        .withStatus(429)
                        .withHeader("Content-Type", MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                        .withBody(problemJson)));

        ConnectorProblemException error = assertThrows(
                ConnectorProblemException.class,
                () -> client.signData(connector, new SignDataRequestV2Dto()));

        assertEquals(ErrorCode.RATE_LIMIT_EXCEEDED, error.getProblemDetail().getErrorCode());
        assertEquals(429, error.getProblemDetail().getStatus());
        assertTrue(error.getProblemDetail().isRetryable());
        assertEquals(20, error.getProblemDetail().getRetryAfterSeconds());
        assertEquals("sign-test-correlation", error.getProblemDetail().getCorrelationId());
        assertEquals(connector, error.getConnector());
    }

    @Test
    void malformedSignResponsePropagatesDecodingException() {
        server.stubFor(WireMock.post("/v2/cryptographyProvider/operations/sign")
                .willReturn(WireMock.okJson("{")));

        assertThrows(DecodingException.class,
                () -> client.signData(connector, new SignDataRequestV2Dto()));
    }

    @ParameterizedTest
    @EnumSource(SynchronousOperation.class)
    void synchronousOperation_postsToMappedConnectorPath(SynchronousOperation operation) throws Exception {
        // given
        server.stubFor(WireMock.post(BASE_PATH + operation.path).willReturn(WireMock.okJson("{}")));

        // when
        invoke(operation);

        // then
        server.verify(WireMock.postRequestedFor(WireMock.urlEqualTo(BASE_PATH + operation.path)));
    }

    @ParameterizedTest
    @EnumSource(AttributeOperation.class)
    void attributeOperation_postsToMappedConnectorPath(AttributeOperation operation) throws Exception {
        // given
        server.stubFor(WireMock.post(BASE_PATH + operation.path).willReturn(WireMock.okJson("[]")));

        // when
        var attributes = invoke(operation);

        // then
        assertTrue(attributes.isEmpty());
        server.verify(WireMock.postRequestedFor(WireMock.urlEqualTo(BASE_PATH + operation.path)));
    }

    @Test
    void encrypt_preservesStructuredConnectorProblem() {
        // given
        var problemJson = """
                {
                  "title": "Rate limit exceeded",
                  "status": 429,
                  "errorCode": "RATE_LIMIT_EXCEEDED",
                  "timestamp": "2026-07-23T10:00:00Z",
                  "correlationId": "encrypt-test-correlation",
                  "retryable": true
                }
                """;
        server.stubFor(WireMock.post(BASE_PATH + "/encrypt")
                .willReturn(WireMock.aResponse()
                        .withStatus(429)
                        .withHeader("Content-Type", MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                        .withBody(problemJson)));

        // when
        Executable encrypt = () -> client.encryptData(connector, new CipherDataRequestV2Dto());

        // then
        var error = assertThrows(ConnectorProblemException.class, encrypt);
        assertEquals(ErrorCode.RATE_LIMIT_EXCEEDED, error.getProblemDetail().getErrorCode());
        assertEquals("encrypt-test-correlation", error.getProblemDetail().getCorrelationId());
    }

    private void invoke(SynchronousOperation operation) throws Exception {
        switch (operation) {
            case ENCRYPT -> client.encryptData(connector, new CipherDataRequestV2Dto());
            case DECRYPT -> client.decryptData(connector, new CipherDataRequestV2Dto());
            case VERIFY -> client.verifyData(connector, new VerifyDataRequestV2Dto());
            case RANDOM -> client.randomData(connector, new RandomDataRequestV2Dto());
        }
    }

    private java.util.List<BaseAttribute> invoke(AttributeOperation operation) throws Exception {
        return switch (operation) {
            case ENCRYPT -> client.listEncryptAttributes(connector, new KeyScopedRequestV2Dto());
            case DECRYPT -> client.listDecryptAttributes(connector, new KeyScopedRequestV2Dto());
            case VERIFY -> client.listVerifyAttributes(connector, new KeyScopedRequestV2Dto());
            case RANDOM -> client.listRandomAttributes(connector, new TokenProfileScopedRequestV2Dto());
        };
    }

    private enum SynchronousOperation {
        ENCRYPT("/encrypt"), DECRYPT("/decrypt"), VERIFY("/verify"), RANDOM("/random");

        private final String path;

        SynchronousOperation(String path) {
            this.path = path;
        }
    }

    private enum AttributeOperation {
        ENCRYPT("/encrypt/attributes"), DECRYPT("/decrypt/attributes"),
        VERIFY("/verify/attributes"), RANDOM("/random/attributes");

        private final String path;

        AttributeOperation(String path) {
            this.path = path;
        }
    }
}
