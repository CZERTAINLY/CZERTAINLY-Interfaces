package com.otilm.api.clients.v2;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.otilm.api.clients.BaseApiClient;
import com.otilm.api.exception.ConnectorProblemException;
import com.otilm.api.model.common.error.ErrorCode;
import com.otilm.api.model.connector.cryptography.v2.operations.SignDataRequestV2Dto;
import com.otilm.api.model.core.connector.ConnectorDto;
import com.otilm.api.model.core.connector.ConnectorStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.*;

class CryptographicOperationsApiClientTest {

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
}
