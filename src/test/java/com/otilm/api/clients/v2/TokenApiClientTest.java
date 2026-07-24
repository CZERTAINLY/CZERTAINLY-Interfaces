package com.otilm.api.clients.v2;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.otilm.api.clients.BaseApiClient;
import com.otilm.api.clients.testutils.ConnectorWireMockServer;
import com.otilm.api.exception.ConnectorException;
import com.otilm.api.exception.ConnectorProblemException;
import com.otilm.api.model.client.attribute.RequestAttributeV2;
import com.otilm.api.model.common.attribute.common.content.AttributeContentType;
import com.otilm.api.model.common.attribute.v2.content.StringAttributeContentV2;
import com.otilm.api.model.common.error.ErrorCode;
import com.otilm.api.model.connector.cryptography.v2.token.TokenScopedRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.token.TokenStatusV2;
import com.otilm.api.model.core.connector.ConnectorDto;
import com.otilm.api.model.core.cryptography.key.KeyUsage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.LoggerFactory;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;

import static com.otilm.api.clients.testutils.ConnectorWireMockServer.aConnectorWireMockServer;
import static com.otilm.api.clients.testutils.ProblemResponseJsonBuilder.aProblemResponse;
import static org.junit.jupiter.api.Assertions.*;

class TokenApiClientTest {

    private TokenApiClient client;
    private ConnectorDto connector;
    private ConnectorWireMockServer server;

    @BeforeEach
    void setUp() {
        client = new TokenApiClient(BaseApiClient.prepareWebClient(), null);
        server = aConnectorWireMockServer();
        connector = server.connectedConnector();
    }

    @AfterEach
    void tearDown() {
        server.close();
    }

    @Test
    void listTokenAttributes_returnsConnectorResponse() throws ConnectorException {
        // given
        server.stubGetJson("/v2/cryptographyProvider/tokens/attributes", "[]");

        // when
        var result = client.listTokenAttributes(connector);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    void getTokenStatus_returnsConnectorStatus() throws ConnectorException {
        // given
        var expectedStatus = TokenStatusV2.CONNECTED;
        server.stubPostJson(
                "/v2/cryptographyProvider/tokens/status",
                "{\"status\":\"Connected\"}");

        // when
        var result = client.getTokenStatus(connector, tokenScopedRequest());

        // then
        assertEquals(expectedStatus, result.getStatus());
    }

    @Test
    void listTokenProfileAttributes_returnsConnectorResponse() throws ConnectorException {
        // given
        server.stubPostJson("/v2/cryptographyProvider/tokens/tokenProfile/attributes", "[]");

        // when
        var result = client.listTokenProfileAttributes(connector, tokenScopedRequest());

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    void listTokenProfileKeyUsages_returnsConnectorResponse() throws ConnectorException {
        // given
        var expectedKeyUsages = List.of(KeyUsage.SIGN, KeyUsage.UNWRAP);
        server.stubPostJson(
                "/v2/cryptographyProvider/tokens/tokenProfile/keyUsages",
                "[\"sign\",\"unwrap\"]");

        // when
        var result = client.listTokenProfileKeyUsages(connector, tokenScopedRequest());

        // then
        assertEquals(expectedKeyUsages, result);
    }

    @Test
    void getTokenStatus_preservesStructuredFailure_withoutExposingRequestContent() {
        // given
        var secret = "expanded-pin-1234";
        var expectedErrorCode = ErrorCode.SERVICE_UNAVAILABLE;
        var expectedRetryAfterSeconds = 30;
        var request = tokenScopedRequestWithSecret(secret);
        stubServiceUnavailableProblem();
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        ListAppender<ILoggingEvent> capturedEvents = new ListAppender<>();
        capturedEvents.start();
        rootLogger.addAppender(capturedEvents);

        try {
            // when
            Executable getTokenStatus = () -> client.getTokenStatus(connector, request);

            // then
            var exception = assertThrows(ConnectorProblemException.class, getTokenStatus);
            assertEquals(expectedErrorCode, exception.getProblemDetail().getErrorCode());
            assertTrue(exception.getProblemDetail().isRetryable());
            assertEquals(expectedRetryAfterSeconds, exception.getProblemDetail().getRetryAfterSeconds());
            assertEquals(connector, exception.getConnector());
            assertFalse(exception.getMessage().contains(secret));
            assertNull(exception.getCause(), "transport internals may contain secret-bearing request data");
            assertTrue(capturedEvents.list.stream()
                    .noneMatch(event -> event.getFormattedMessage().contains(secret)));
        } finally {
            rootLogger.detachAppender(capturedEvents);
            capturedEvents.stop();
        }
    }

    @Test
    void getTokenStatus_throwsDecodingException_forMalformedResponse() {
        // given
        var malformedJson = "{";
        server.stubPostJson("/v2/cryptographyProvider/tokens/status", malformedJson);

        // when
        Executable getTokenStatus = () -> client.getTokenStatus(connector, tokenScopedRequest());

        // then
        assertThrows(DecodingException.class, getTokenStatus);
    }

    private static TokenScopedRequestV2Dto tokenScopedRequest() {
        var request = new TokenScopedRequestV2Dto();
        request.setTokenAttributes(List.of());
        return request;
    }

    private static TokenScopedRequestV2Dto tokenScopedRequestWithSecret(String secret) {
        var request = new TokenScopedRequestV2Dto();
        request.setTokenAttributes(List.of(new RequestAttributeV2(
                UUID.randomUUID(),
                "pin",
                AttributeContentType.STRING,
                List.of(new StringAttributeContentV2(secret)))));
        return request;
    }

    private void stubServiceUnavailableProblem() {
        var problemJson = aProblemResponse()
                .withErrorCode(ErrorCode.SERVICE_UNAVAILABLE)
                .withTitle("Service unavailable")
                .withStatus(503)
                .withDetail("Token provider is temporarily unavailable")
                .withTimestamp("2026-07-23T10:00:00Z")
                .withCorrelationId("token-test-correlation")
                .withRetryable(true)
                .withRetryAfterSeconds(30)
                .build();
        server.stubPostJson(
                "/v2/cryptographyProvider/tokens/status",
                503,
                MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                problemJson);
    }
}
