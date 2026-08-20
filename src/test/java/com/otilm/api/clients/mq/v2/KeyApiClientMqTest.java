package com.otilm.api.clients.mq.v2;

import com.otilm.api.exception.ConnectorException;
import com.otilm.api.model.client.cryptography.key.KeyRequestType;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.connector.common.v2.OperationExecutionMode;
import com.otilm.api.model.connector.common.v2.OperationStatus;
import com.otilm.api.model.connector.cryptography.v2.OperationResponseValidator;
import com.otilm.api.model.connector.cryptography.v2.OperationTrackingRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.CreateKeyAttributesRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.CreateKeyRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.DestroyKeyRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyCreationResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyCreationStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyDestructionStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.SecretKeyDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.SecretKeyOperationStatusResponseV2Dto;
import com.otilm.api.model.core.connector.ConnectorDto;
import com.otilm.api.testsupport.RecordingProxyClient;
import com.otilm.api.testsupport.RecordingProxyClient.Invocation;
import com.otilm.api.testsupport.ValidatorFixture;
import java.util.List;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.otilm.api.model.connector.cryptography.v2.utils.CryptographyDtoFixtures.validMetadata;
import static com.otilm.api.model.connector.cryptography.v2.utils.CryptographyDtoFixtures.validMetadataAttribute;
import static com.otilm.api.model.connector.cryptography.v2.utils.CryptographyDtoFixtures.validSecretKeyDataResponse;
import static com.otilm.api.model.connector.cryptography.v2.utils.CryptographyDtoFixtures.withValidTokenProfileScope;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KeyApiClientMqTest {

    private static final String BASE_PATH = "/v2/cryptographyProvider/keys";
    private static final String CREATE_ATTRIBUTES_PATH = BASE_PATH + "/create/attributes";
    private static final String CREATE_STATUS_PATH = BASE_PATH + "/create/status";
    private static final String CREATE_CANCEL_PATH = BASE_PATH + "/create/cancel";
    private static final String DESTROY_PATH = BASE_PATH + "/destroy";
    private static final String DESTROY_STATUS_PATH = BASE_PATH + "/destroy/status";
    private static final String DESTROY_CANCEL_PATH = BASE_PATH + "/destroy/cancel";
    private static final String KEY_CREATION_ID = "key-creation-1";

    @AutoClose
    private static final ValidatorFixture VALIDATORS = new ValidatorFixture();

    private KeyApiClient client;
    private ConnectorDto connector;
    private RecordingProxyClient proxyClient;

    @BeforeEach
    void setUp() {
        proxyClient = new RecordingProxyClient();
        connector = new ConnectorDto();
        OperationResponseValidator responseValidator = new OperationResponseValidator(VALIDATORS.validator());
        client = new KeyApiClient(proxyClient, responseValidator);
    }

    @Test
    void listCreateKeyAttributes_delegatesPostAndReturnsAttributes() throws ConnectorException {
        // given
        CreateKeyAttributesRequestV2Dto request = createKeyAttributesRequest();
        BaseAttribute attribute = validMetadataAttribute();
        proxyClient.respondWith(new BaseAttribute[]{attribute});

        // when
        List<BaseAttribute> result = client.listCreateKeyAttributes(connector, request);

        // then
        assertEquals(List.of(attribute), result);
        assertPlainInvocation(CREATE_ATTRIBUTES_PATH, request, BaseAttribute[].class);
    }

    @Test
    void listCreateKeyAttributes_rejectsInvalidResponse() {
        // given
        proxyClient.respondWith(new BaseAttribute[]{null});

        // when
        Executable call = () -> client.listCreateKeyAttributes(connector, createKeyAttributesRequest());

        // then
        assertValidationFailure(call);
    }

    @Test
    void createKey_returnsSynchronousResponse() throws ConnectorException {
        // given
        CreateKeyRequestV2Dto request = createKeyRequest(OperationExecutionMode.SYNCHRONOUS);
        SecretKeyDataResponseV2Dto body = validSecretKeyDataResponse();
        proxyClient.respondWithEntity(ResponseEntity.ok(body));

        // when
        ResponseEntity<KeyCreationResponseV2Dto> result = client.createKey(connector, request);

        // then
        assertSame(body, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEntityInvocation(BASE_PATH, request, KeyCreationResponseV2Dto.class);
    }

    @Test
    void createKey_returnsAsynchronousResponse() throws ConnectorException {
        // given
        CreateKeyRequestV2Dto request = createKeyRequest(OperationExecutionMode.ASYNCHRONOUS);
        SecretKeyDataResponseV2Dto body = new SecretKeyDataResponseV2Dto();
        body.setOperationMeta(validMetadata());
        proxyClient.respondWithEntity(ResponseEntity.accepted().body(body));

        // when
        ResponseEntity<KeyCreationResponseV2Dto> result = client.createKey(connector, request);

        // then
        assertSame(body, result.getBody());
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        assertEntityInvocation(BASE_PATH, request, KeyCreationResponseV2Dto.class);
    }

    @Test
    void createKey_rejectsInvalidResponse() {
        // given
        SecretKeyDataResponseV2Dto responseWithoutKeyData = new SecretKeyDataResponseV2Dto();
        proxyClient.respondWithEntity(ResponseEntity.ok(responseWithoutKeyData));

        // when
        Executable call = () -> client.createKey(connector, createKeyRequest(OperationExecutionMode.SYNCHRONOUS));

        // then
        assertValidationFailure(call);
    }

    @Test
    void createKey_rejectsMismatchedKeyRequestType() {
        // given
        CreateKeyRequestV2Dto keyPairRequest = createKeyRequest(KeyRequestType.KEY_PAIR,
                OperationExecutionMode.SYNCHRONOUS);
        proxyClient.respondWithEntity(ResponseEntity.ok(validSecretKeyDataResponse()));

        // when
        Executable call = () -> client.createKey(connector, keyPairRequest);

        // then
        assertValidationFailure(call);
    }

    @Test
    void getCreateKeyStatus_delegatesPostAndReturnsStatus() throws ConnectorException {
        // given
        OperationTrackingRequestV2Dto request = keyOperationRequest();
        SecretKeyOperationStatusResponseV2Dto response = new SecretKeyOperationStatusResponseV2Dto();
        response.setStatus(OperationStatus.IN_PROGRESS);
        proxyClient.respondWith(response);

        // when
        KeyCreationStatusResponseV2Dto result = client.getCreateKeyStatus(connector, request);

        // then
        assertSame(response, result);
        assertPlainInvocation(CREATE_STATUS_PATH, request, KeyCreationStatusResponseV2Dto.class);
    }

    @Test
    void getCreateKeyStatus_rejectsInvalidResponse() {
        // given
        proxyClient.respondWith(new SecretKeyOperationStatusResponseV2Dto());

        // when
        Executable call = () -> client.getCreateKeyStatus(connector, keyOperationRequest());

        // then
        assertValidationFailure(call);
    }

    @Test
    void cancelCreateKey_delegatesPostAndPreservesStatus() throws ConnectorException {
        // given
        OperationTrackingRequestV2Dto request = keyOperationRequest();
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        proxyClient.respondWithEntity(response);

        // when
        ResponseEntity<Void> result = client.cancelCreateKey(connector, request);

        // then
        assertSame(response, result);
        assertEntityInvocation(CREATE_CANCEL_PATH, request, Void.class);
    }

    @Test
    void destroyKey_returnsSynchronousResponse() throws ConnectorException {
        // given
        DestroyKeyRequestV2Dto request = destroyKeyRequest(OperationExecutionMode.SYNCHRONOUS);
        KeyOperationResponseV2Dto body = new KeyOperationResponseV2Dto();
        ResponseEntity<KeyOperationResponseV2Dto> response = ResponseEntity.ok(body);
        proxyClient.respondWithEntity(response);

        // when
        ResponseEntity<KeyOperationResponseV2Dto> result = client.destroyKey(connector, request);

        // then
        assertSame(response, result);
        assertEntityInvocation(DESTROY_PATH, request, KeyOperationResponseV2Dto.class);
    }

    @Test
    void destroyKey_returnsAsynchronousResponse() throws ConnectorException {
        // given
        DestroyKeyRequestV2Dto request = destroyKeyRequest(OperationExecutionMode.ASYNCHRONOUS);
        KeyOperationResponseV2Dto body = new KeyOperationResponseV2Dto();
        body.setOperationMeta(validMetadata());
        ResponseEntity<KeyOperationResponseV2Dto> response = ResponseEntity.accepted().body(body);
        proxyClient.respondWithEntity(response);

        // when
        ResponseEntity<KeyOperationResponseV2Dto> result = client.destroyKey(connector, request);

        // then
        assertSame(response, result);
        assertEntityInvocation(DESTROY_PATH, request, KeyOperationResponseV2Dto.class);
    }

    @Test
    void destroyKey_rejectsInvalidResponse() {
        // given
        KeyOperationResponseV2Dto responseWithoutOperationMetadata = new KeyOperationResponseV2Dto();
        proxyClient.respondWithEntity(ResponseEntity.accepted().body(responseWithoutOperationMetadata));

        // when
        Executable call = () -> client.destroyKey(connector, destroyKeyRequest(OperationExecutionMode.ASYNCHRONOUS));

        // then
        assertValidationFailure(call);
    }

    @Test
    void getDestroyKeyStatus_delegatesPostAndReturnsStatus() throws ConnectorException {
        // given
        OperationTrackingRequestV2Dto request = keyOperationRequest();
        KeyDestructionStatusResponseV2Dto response = new KeyDestructionStatusResponseV2Dto();
        response.setStatus(OperationStatus.IN_PROGRESS);
        proxyClient.respondWith(response);

        // when
        KeyOperationStatusResponseV2Dto result = client.getDestroyKeyStatus(connector, request);

        // then
        assertSame(response, result);
        assertPlainInvocation(DESTROY_STATUS_PATH, request, KeyDestructionStatusResponseV2Dto.class);
    }

    @Test
    void getDestroyKeyStatus_rejectsInvalidResponse() {
        // given
        proxyClient.respondWith(new KeyDestructionStatusResponseV2Dto());

        // when
        Executable call = () -> client.getDestroyKeyStatus(connector, keyOperationRequest());

        // then
        assertValidationFailure(call);
    }

    @Test
    void cancelDestroyKey_delegatesPostAndPreservesStatus() throws ConnectorException {
        // given
        OperationTrackingRequestV2Dto request = keyOperationRequest();
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        proxyClient.respondWithEntity(response);

        // when
        ResponseEntity<Void> result = client.cancelDestroyKey(connector, request);

        // then
        assertSame(response, result);
        assertEntityInvocation(DESTROY_CANCEL_PATH, request, Void.class);
    }

    @Test
    void createKey_wrapsProxyRuntimeFailure() {
        // given
        RuntimeException proxyFailure = new IllegalStateException("proxy failure");
        proxyClient.failWith(proxyFailure);

        // when
        Executable call = () -> client.createKey(connector, createKeyRequest(OperationExecutionMode.SYNCHRONOUS));

        // then
        ConnectorException exception = assertThrows(ConnectorException.class, call);
        assertSame(proxyFailure, exception.getCause());
        assertSame(connector, exception.getConnector());
    }

    private void assertPlainInvocation(String path, Object body, Class<?> responseType) {
        assertInvocation(path, body, responseType, false);
    }

    private void assertEntityInvocation(String path, Object body, Class<?> responseType) {
        assertInvocation(path, body, responseType, true);
    }

    private void assertInvocation(String path, Object body, Class<?> responseType, boolean entityResponse) {
        Invocation invocation = proxyClient.invocation();
        assertSame(connector, invocation.connector());
        assertEquals(path, invocation.path());
        assertEquals("POST", invocation.method());
        assertSame(body, invocation.body());
        assertEquals(responseType, invocation.responseType());
        if (entityResponse) {
            assertTrue(invocation.entityResponse());
        } else {
            assertFalse(invocation.entityResponse());
        }
    }

    private void assertValidationFailure(Executable call) {
        ConnectorException exception = assertThrows(ConnectorException.class, call);
        assertInstanceOf(IllegalArgumentException.class, exception.getCause());
        assertSame(connector, exception.getConnector());
    }

    private static CreateKeyAttributesRequestV2Dto createKeyAttributesRequest() {
        CreateKeyAttributesRequestV2Dto request = withValidTokenProfileScope(new CreateKeyAttributesRequestV2Dto());
        request.setKeyRequestType(KeyRequestType.SECRET);
        return request;
    }

    private static CreateKeyRequestV2Dto createKeyRequest(OperationExecutionMode mode) {
        return createKeyRequest(KeyRequestType.SECRET, mode);
    }

    private static CreateKeyRequestV2Dto createKeyRequest(KeyRequestType keyRequestType, OperationExecutionMode mode) {
        CreateKeyRequestV2Dto request = withValidTokenProfileScope(new CreateKeyRequestV2Dto());
        request.setKeyRequestType(keyRequestType);
        request.setExecutionMode(mode);
        request.setKeyCreationId(KEY_CREATION_ID);
        request.setCreateKeyAttributes(List.of());
        return request;
    }

    private static DestroyKeyRequestV2Dto destroyKeyRequest(OperationExecutionMode mode) {
        DestroyKeyRequestV2Dto request = withValidTokenProfileScope(new DestroyKeyRequestV2Dto());
        request.setKeyMeta(validMetadata());
        request.setExecutionMode(mode);
        return request;
    }

    private static OperationTrackingRequestV2Dto keyOperationRequest() {
        OperationTrackingRequestV2Dto request = new OperationTrackingRequestV2Dto();
        request.setOperationMeta(validMetadata());
        return request;
    }
}
