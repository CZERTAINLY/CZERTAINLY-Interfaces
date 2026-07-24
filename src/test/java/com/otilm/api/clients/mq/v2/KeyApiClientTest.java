package com.otilm.api.clients.mq.v2;

import com.otilm.api.exception.ConnectorException;
import com.otilm.api.model.client.cryptography.key.KeyRequestType;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.common.enums.cryptography.KeyAlgorithm;
import com.otilm.api.model.connector.common.v2.OperationExecutionMode;
import com.otilm.api.model.connector.cryptography.v2.TokenProfileScopedRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.*;
import com.otilm.api.model.core.connector.ConnectorDto;
import com.otilm.api.model.core.cryptography.key.KeyUsage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

import static com.otilm.api.clients.mq.v2.RecordingProxyClient.aRecordingProxyClient;
import static com.otilm.api.model.connector.v2.cryptography.MetadataTestUtils.stringMetadata;
import static org.junit.jupiter.api.Assertions.*;

class KeyApiClientTest {

    private RecordingProxyClient proxy;
    private KeyApiClient client;
    private ConnectorDto connector;

    @BeforeEach
    void setUp() {
        proxy = aRecordingProxyClient();
        client = new KeyApiClient(proxy);
        connector = new ConnectorDto();
    }

    @Test
    void listSupportedKeyTypes_returnsConnectorResponse() throws Exception {
        // given
        var request = tokenProfileScopedRequest();
        var supportedTypes = new KeyRequestType[]{KeyRequestType.SECRET, KeyRequestType.KEY_PAIR};
        proxy.respondWith(supportedTypes);

        // when
        var result = client.listSupportedKeyTypes(connector, request);

        // then
        assertEquals(List.of(supportedTypes), result);
        proxy.assertCall(connector, "/v2/cryptographyProvider/keys/types", "POST",
                request, KeyRequestType[].class);
    }

    @Test
    void listCreateKeyAttributes_usesSecretKeyPath_forSecretKeyType() throws Exception {
        // given
        var request = tokenProfileScopedRequest();
        proxy.respondWith(new BaseAttribute[0]);

        // when
        var result = client.listCreateKeyAttributes(connector, KeyRequestType.SECRET, request);

        // then
        assertTrue(result.isEmpty());
        proxy.assertCall(connector, "/v2/cryptographyProvider/keys/secret/attributes", "POST",
                request, BaseAttribute[].class);
    }

    @Test
    void listCreateKeyAttributes_usesKeyPairPath_forKeyPairType() throws Exception {
        // given
        var request = tokenProfileScopedRequest();
        proxy.respondWith(new BaseAttribute[0]);

        // when
        var result = client.listCreateKeyAttributes(connector, KeyRequestType.KEY_PAIR, request);

        // then
        assertTrue(result.isEmpty());
        proxy.assertCall(connector, "/v2/cryptographyProvider/keys/pair/attributes", "POST",
                request, BaseAttribute[].class);
    }

    @Test
    void createSecretKey_returnsCompletedResponse_forSynchronousRequest() throws Exception {
        // given
        var completedResponse = completedSecretKey();
        var request = createKeyRequest(OperationExecutionMode.SYNCHRONOUS);
        proxy.respondWithEntity(ResponseEntity.ok(completedResponse));

        // when
        var result = client.createSecretKey(connector, request);

        // then
        assertSame(completedResponse, result.getBody());
        proxy.assertCall(connector, "/v2/cryptographyProvider/keys/secret", "POST",
                request, SecretKeyDataResponseV2Dto.class);
    }

    @Test
    void createSecretKey_preservesKeyCreationId() throws Exception {
        // given
        var keyCreationId = "6c87b73a-5659-4d17-b625-abc72cd94150";
        var request = createKeyRequest(OperationExecutionMode.SYNCHRONOUS);
        request.setKeyCreationId(keyCreationId);
        proxy.respondWithEntity(ResponseEntity.ok(completedSecretKey()));

        // when
        client.createSecretKey(connector, request);

        // then
        var sentRequest = proxy.recordedBody(CreateKeyRequestV2Dto.class);
        assertEquals(keyCreationId, sentRequest.getKeyCreationId());
    }

    @Test
    void createSecretKey_returnsAcceptedResponse_forAsynchronousRequest() throws Exception {
        // given
        var pendingResponse = pendingSecretKey();
        var request = createKeyRequest(OperationExecutionMode.ASYNCHRONOUS);
        proxy.respondWithEntity(ResponseEntity.accepted().body(pendingResponse));

        // when
        var result = client.createSecretKey(connector, request);

        // then
        assertEquals(202, result.getStatusCode().value());
        assertSame(pendingResponse, result.getBody());
    }

    @Test
    void createKeyPair_returnsCompletedRoleSpecificKeys() throws Exception {
        // given
        var completedResponse = completedKeyPair();
        var request = createKeyRequest(OperationExecutionMode.SYNCHRONOUS);
        proxy.respondWithEntity(ResponseEntity.ok(completedResponse));

        // when
        var result = client.createKeyPair(connector, request);

        // then
        assertSame(completedResponse, result.getBody());
        Assertions.assertNotNull(result.getBody());
        assertEquals(KeyAlgorithm.RSA, result.getBody().getPrivateKeyData().getKeyData().getAlgorithm());
        assertEquals(KeyAlgorithm.RSA, result.getBody().getPublicKeyData().getKeyData().getAlgorithm());
        proxy.assertCall(connector, "/v2/cryptographyProvider/keys/pair", "POST",
                request, KeyPairDataResponseV2Dto.class);
    }

    @Test
    void createSecretKey_throwsConnectorException_forMalformedCompletedKeyData() {
        // given
        var malformedResponse = new SecretKeyDataResponseV2Dto();
        malformedResponse.setKeyData(new SecretKeyDataV2Dto());
        proxy.respondWithEntity(ResponseEntity.ok(malformedResponse));

        // when
        Executable createSecretKey = () -> client.createSecretKey(
                connector, createKeyRequest(OperationExecutionMode.SYNCHRONOUS));

        // then
        var exception = assertThrows(ConnectorException.class, createSecretKey);
        assertTrue(exception.getMessage().contains("invalid completed key data"));
    }

    @Test
    void listSupportedKeyTypes_throwsConnectorException_forNullTypeEntry() {
        // given
        proxy.respondWith(new KeyRequestType[]{KeyRequestType.SECRET, null});

        // when
        Executable listSupportedKeyTypes = () -> client.listSupportedKeyTypes(
                connector, tokenProfileScopedRequest());

        // then
        assertThrows(ConnectorException.class, listSupportedKeyTypes);
    }

    private static TokenProfileScopedRequestV2Dto tokenProfileScopedRequest() {
        var request = new TokenProfileScopedRequestV2Dto();
        request.setKeyUsages(Set.of(KeyUsage.SIGN));
        return request;
    }

    private static CreateKeyRequestV2Dto createKeyRequest(OperationExecutionMode executionMode) {
        var request = new CreateKeyRequestV2Dto();
        request.setExecutionMode(executionMode);
        return request;
    }

    private static SecretKeyDataResponseV2Dto completedSecretKey() {
        var keyData = new SecretKeyDataV2Dto();
        keyData.setAlgorithm(KeyAlgorithm.AES);
        keyData.setLength(256);

        var response = new SecretKeyDataResponseV2Dto();
        response.setKeyData(keyData);
        response.setKeyMeta(List.of(stringMetadata("keyId", "secret-1")));
        return response;
    }

    private static SecretKeyDataResponseV2Dto pendingSecretKey() {
        var response = new SecretKeyDataResponseV2Dto();
        response.setOperationMeta(List.of(stringMetadata("operationId", "123")));
        return response;
    }

    private static KeyPairDataResponseV2Dto completedKeyPair() {
        var privateKey = new PrivateKeyDataV2Dto();
        privateKey.setAlgorithm(KeyAlgorithm.RSA);
        privateKey.setLength(2048);
        var privateResponse = new PrivateKeyDataResponseV2Dto();
        privateResponse.setKeyData(privateKey);
        privateResponse.setKeyMeta(List.of(stringMetadata("keyId", "private-1")));

        var publicKey = new PublicKeyDataV2Dto();
        publicKey.setAlgorithm(KeyAlgorithm.RSA);
        publicKey.setLength(2048);
        var publicResponse = new PublicKeyDataResponseV2Dto();
        publicResponse.setKeyData(publicKey);
        publicResponse.setKeyMeta(List.of(stringMetadata("keyId", "public-1")));

        var response = new KeyPairDataResponseV2Dto();
        response.setPrivateKeyData(privateResponse);
        response.setPublicKeyData(publicResponse);
        response.setKeyPairMeta(List.of(stringMetadata("pairId", "pair-1")));
        return response;
    }

}
