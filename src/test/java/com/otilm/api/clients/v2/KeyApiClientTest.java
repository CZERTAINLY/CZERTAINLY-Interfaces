package com.otilm.api.clients.v2;

import com.otilm.api.clients.BaseApiClient;
import com.otilm.api.clients.testutils.ConnectorWireMockServer;
import com.otilm.api.exception.ConnectorException;
import com.otilm.api.exception.ConnectorProblemException;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.client.cryptography.key.KeyRequestType;
import com.otilm.api.model.common.error.ErrorCode;
import com.otilm.api.model.connector.common.v2.OperationExecutionMode;
import com.otilm.api.model.connector.cryptography.v2.TokenProfileScopedRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.CreateKeyRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.PrivateKeyDataV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.PublicKeyDataV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.SecretKeyDataV2Dto;
import com.otilm.api.model.core.connector.ConnectorDto;
import com.otilm.api.model.core.connector.ConnectorStatus;
import com.otilm.api.model.core.cryptography.key.KeyUsage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.MediaType;

import java.security.KeyPairGenerator;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import static com.otilm.api.clients.testutils.ConnectorWireMockServer.aConnectorWireMockServer;
import static com.otilm.api.clients.testutils.ProblemResponseJsonBuilder.aProblemResponse;
import static com.otilm.api.clients.v2.builders.KeyResponseJsonBuilder.aKeyPairResponse;
import static com.otilm.api.clients.v2.builders.KeyResponseJsonBuilder.aSecretKeyResponse;
import static com.otilm.api.clients.v2.builders.OperationResponseJsonBuilder.anOperationResponse;
import static org.junit.jupiter.api.Assertions.*;

class KeyApiClientTest {
    private KeyApiClient client;
    private ConnectorDto connector;
    private ConnectorWireMockServer server;

    @BeforeEach
    void setUp() {
        client = new KeyApiClient(BaseApiClient.prepareWebClient(), null);
        server = aConnectorWireMockServer();
        connector = server.connectedConnector();
    }

    @AfterEach
    void tearDown() {
        server.close();
    }

    @Test
    void listSupportedKeyTypes_returnsConnectorResponse() throws ConnectorException {
        // given
        server.stubPostJson("/v2/cryptographyProvider/keys/types", "[\"secret\",\"keyPair\"]");

        // when
        var result = client.listSupportedKeyTypes(connector, tokenProfileScopedRequest());

        // then
        assertEquals(List.of(KeyRequestType.SECRET, KeyRequestType.KEY_PAIR), result);
    }

    @Test
    void listCreateKeyAttributes_returnsSecretKeyAttributes() throws ConnectorException {
        // given
        server.stubPostJson("/v2/cryptographyProvider/keys/secret/attributes", "[]");

        // when
        var result = client.listCreateKeyAttributes(
                connector, KeyRequestType.SECRET, tokenProfileScopedRequest());

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    void listCreateKeyAttributes_returnsKeyPairAttributes() throws ConnectorException {
        // given
        server.stubPostJson("/v2/cryptographyProvider/keys/pair/attributes", "[]");

        // when
        var result = client.listCreateKeyAttributes(
                connector, KeyRequestType.KEY_PAIR, tokenProfileScopedRequest());

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    void createSecretKey_deserializesSecretKeyData() throws Exception {
        // given
        stubCompletedSecretKey();

        // when
        var response = client.createSecretKey(connector, synchronousCreateRequest());

        // then
        assertInstanceOf(SecretKeyDataV2Dto.class, assertResponseBody(response).getKeyData());
    }

    @Test
    void createKeyPair_deserializesRoleSpecificData_andPreservesCreationId() throws Exception {
        // given
        var keyCreationId = "6c87b73a-5659-4d17-b625-abc72cd94150";
        var request = synchronousCreateRequest();
        request.setKeyCreationId(keyCreationId);
        stubCompletedKeyPair();

        // when
        var response = client.createKeyPair(connector, request);

        // then
        var responseBody = assertResponseBody(response);
        assertInstanceOf(PrivateKeyDataV2Dto.class, responseBody.getPrivateKeyData().getKeyData());
        assertInstanceOf(PublicKeyDataV2Dto.class, responseBody.getPublicKeyData().getKeyData());
        server.verifyPostBody(
                "/v2/cryptographyProvider/keys/pair",
                "$.keyCreationId",
                keyCreationId);
    }

    @Test
    void listSupportedKeyTypes_throwsConnectorException_forNullTypeEntry() {
        // given
        server.stubPostJson("/v2/cryptographyProvider/keys/types", "[\"secret\",null]");

        // when
        Executable listSupportedKeyTypes = () -> client.listSupportedKeyTypes(
                connector, tokenProfileScopedRequest());

        // then
        assertThrows(ConnectorException.class, listSupportedKeyTypes);
    }

    @Test
    void createSecretKey_returnsAcceptedResponse_forAsynchronousRequest() throws Exception {
        // given
        var expectedOperationId = "123";
        var responseBody = anOperationResponse()
                .withOperationMetadata("operationId", expectedOperationId)
                .build();
        server.stubPostJson(
                "/v2/cryptographyProvider/keys/secret",
                202,
                MediaType.APPLICATION_JSON_VALUE,
                responseBody);
        var request = createKeyRequest(OperationExecutionMode.ASYNCHRONOUS);

        // when
        var response = client.createSecretKey(connector, request);

        // then
        assertEquals(202, response.getStatusCode().value());
        assertEquals(expectedOperationId, assertResponseBody(response).getOperationMeta().get(0)
                .getContent().get(0).getData());
    }

    @Test
    void createSecretKey_preservesStructuredConnectorProblem() {
        // given
        var expectedCorrelationId = "key-test-correlation";
        var expectedRetryAfterSeconds = 15;
        var problemJson = aProblemResponse()
                .withErrorCode(ErrorCode.RATE_LIMIT_EXCEEDED)
                .withTitle("Rate limit exceeded")
                .withStatus(429)
                .withDetail("Provider request quota exhausted")
                .withTimestamp("2026-07-23T10:00:00Z")
                .withCorrelationId(expectedCorrelationId)
                .withRetryable(true)
                .withRetryAfterSeconds(expectedRetryAfterSeconds)
                .build();
        server.stubPostJson(
                "/v2/cryptographyProvider/keys/secret",
                429,
                MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                problemJson);

        // when
        Executable createSecretKey = () -> client.createSecretKey(connector, synchronousCreateRequest());

        // then
        var exception = assertThrows(ConnectorProblemException.class, createSecretKey);
        assertEquals(ErrorCode.RATE_LIMIT_EXCEEDED, exception.getProblemDetail().getErrorCode());
        assertEquals(429, exception.getProblemDetail().getStatus());
        assertTrue(exception.getProblemDetail().isRetryable());
        assertEquals(expectedRetryAfterSeconds, exception.getProblemDetail().getRetryAfterSeconds());
        assertEquals(expectedCorrelationId, exception.getProblemDetail().getCorrelationId());
        assertEquals(connector, exception.getConnector());
    }

    @Test
    void createSecretKey_throwsValidationException_forDisconnectedConnector() {
        // given
        connector.setStatus(ConnectorStatus.WAITING_FOR_APPROVAL);

        // when
        Executable createSecretKey = () -> client.createSecretKey(connector, synchronousCreateRequest());

        // then
        assertThrows(ValidationException.class, createSecretKey);
    }

    @Test
    void createSecretKey_throwsDecodingException_forPublicKeyResponse() {
        // given
        var responseBody = aSecretKeyResponse()
                .withKeyType("Public")
                .withKeyAlgorithm("RSA")
                .withKeyLength(2048)
                .build();
        server.stubPostJson("/v2/cryptographyProvider/keys/secret", responseBody);

        // when
        Executable createSecretKey = () -> client.createSecretKey(connector, synchronousCreateRequest());

        // then
        assertThrows(DecodingException.class, createSecretKey);
    }

    @Test
    void createSecretKey_throwsDecodingException_forLegacySecretKeyMaterial() {
        // given
        var responseBody = aSecretKeyResponse()
                .withKeyMaterial("Raw", "c2VjcmV0")
                .build();
        server.stubPostJson("/v2/cryptographyProvider/keys/secret", responseBody);

        // when
        Executable createSecretKey = () -> client.createSecretKey(connector, synchronousCreateRequest());

        // then
        assertThrows(DecodingException.class, createSecretKey);
    }

    @ParameterizedTest
    @ValueSource(strings = {"PrivateKeyInfo", "EncryptedPrivateKeyInfo"})
    void createKeyPair_throwsDecodingException_forPrivateKeyMaterial(String privateKeyFormat) {
        // given
        var responseBody = aKeyPairResponse()
                .withPrivateKeyMaterial(privateKeyFormat, "cHJpdmF0ZQ==")
                .build();
        server.stubPostJson("/v2/cryptographyProvider/keys/pair", responseBody);

        // when
        Executable createKeyPair = () -> client.createKeyPair(connector, synchronousCreateRequest());

        // then
        assertThrows(DecodingException.class, createKeyPair);
    }

    @Test
    void createSecretKey_throwsDecodingException_forKeyMaterialOnOuterEnvelope() {
        // given
        var responseBody = aSecretKeyResponse()
                .withOuterKeyMaterial("c2VjcmV0")
                .build();
        server.stubPostJson("/v2/cryptographyProvider/keys/secret", responseBody);

        // when
        Executable createSecretKey = () -> client.createSecretKey(connector, synchronousCreateRequest());

        // then
        assertThrows(DecodingException.class, createSecretKey);
    }

    @Test
    void createSecretKey_throwsConnectorException_forMalformedMetadata() {
        // given
        var responseBody = aSecretKeyResponse()
                .withMalformedKeyMetadata("leak", "c2VjcmV0LWtleQ==")
                .build();
        server.stubPostJson("/v2/cryptographyProvider/keys/secret", responseBody);

        // when
        Executable createSecretKey = () -> client.createSecretKey(connector, synchronousCreateRequest());

        // then
        assertThrows(ConnectorException.class, createSecretKey);
    }

    @Test
    void createSecretKey_throwsConnectorException_forMalformedCompletedDescriptor() {
        // given
        var responseBody = aSecretKeyResponse()
                .withoutKeyAlgorithm()
                .build();
        server.stubPostJson("/v2/cryptographyProvider/keys/secret", responseBody);

        // when
        Executable createSecretKey = () -> client.createSecretKey(connector, synchronousCreateRequest());

        // then
        var exception = assertThrows(ConnectorException.class, createSecretKey);
        assertTrue(exception.getMessage().contains("invalid completed key data"));
    }

    @Test
    void createKeyPair_throwsConnectorException_whenPublicKeyDoesNotMatchAlgorithm() throws Exception {
        // given
        var rsaSpki = Base64.getEncoder().encodeToString(
                KeyPairGenerator.getInstance("RSA").generateKeyPair().getPublic().getEncoded());
        var responseBody = aKeyPairResponse()
                .withKeyPairAlgorithm("ECDSA")
                .withKeyPairLength(256)
                .withPublicKeySpki(rsaSpki)
                .build();
        server.stubPostJson("/v2/cryptographyProvider/keys/pair", responseBody);

        // when
        Executable createKeyPair = () -> client.createKeyPair(connector, synchronousCreateRequest());

        // then
        var exception = assertThrows(ConnectorException.class, createKeyPair);
        assertTrue(exception.getMessage().contains("invalid completed key data"));
    }

    @Test
    void createKeyPair_throwsDecodingException_forSwappedKeyRoles() {
        // given
        var responseBody = aKeyPairResponse()
                .withPrivateKeyType("Public")
                .withPublicKeyType("Private")
                .build();
        server.stubPostJson("/v2/cryptographyProvider/keys/pair", responseBody);

        // when
        Executable createKeyPair = () -> client.createKeyPair(connector, synchronousCreateRequest());

        // then
        assertThrows(DecodingException.class, createKeyPair);
    }

    private static CreateKeyRequestV2Dto synchronousCreateRequest() {
        return createKeyRequest(OperationExecutionMode.SYNCHRONOUS);
    }

    private static CreateKeyRequestV2Dto createKeyRequest(OperationExecutionMode executionMode) {
        var request = new CreateKeyRequestV2Dto();
        request.setExecutionMode(executionMode);
        request.setTokenAttributes(List.of());
        request.setTokenProfileAttributes(List.of());
        request.setCreateKeyAttributes(List.of());
        return request;
    }

    private static TokenProfileScopedRequestV2Dto tokenProfileScopedRequest() {
        var request = new TokenProfileScopedRequestV2Dto();
        request.setTokenAttributes(List.of());
        request.setTokenProfileAttributes(List.of());
        request.setKeyUsages(Set.of(KeyUsage.SIGN));
        return request;
    }

    private static <T> T assertResponseBody(org.springframework.http.ResponseEntity<T> response) {
        assertNotNull(response.getBody());
        return response.getBody();
    }

    private void stubCompletedSecretKey() {
        var responseBody = aSecretKeyResponse()
                .withKeyMetadata("keyId", "secret-1")
                .build();
        server.stubPostJson("/v2/cryptographyProvider/keys/secret", responseBody);
    }

    private void stubCompletedKeyPair() {
        var responseBody = aKeyPairResponse()
                .withKeyPairMetadata("pairId", "pair-1")
                .withPrivateKeyMetadata("keyId", "private-1")
                .withPublicKeyMetadata("keyId", "public-1")
                .withKeyPairLength(3072)
                .build();
        server.stubPostJson("/v2/cryptographyProvider/keys/pair", responseBody);
    }
}
