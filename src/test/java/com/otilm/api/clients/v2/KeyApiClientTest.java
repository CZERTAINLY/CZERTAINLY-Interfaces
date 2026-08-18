package com.otilm.api.clients.v2;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder;
import com.otilm.api.clients.BaseApiClient;
import com.otilm.api.clients.cryptography.v2.KeyApiClient;
import com.otilm.api.exception.ConnectorException;
import com.otilm.api.exception.ConnectorServerException;
import com.otilm.api.model.client.cryptography.key.KeyRequestType;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.common.enums.cryptography.KeyAlgorithm;
import com.otilm.api.model.connector.common.v2.OperationExecutionMode;
import com.otilm.api.model.connector.common.v2.OperationStatus;
import com.otilm.api.model.connector.cryptography.v2.OperationResponseValidator;
import com.otilm.api.model.connector.cryptography.v2.key.CreateKeyAttributesRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.CreateKeyRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.DestroyKeyRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyCreationResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyCreationStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyDestructionStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.SecretKeyDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.SecretKeyOperationStatusResponseV2Dto;
import com.otilm.api.model.core.connector.ConnectorDto;
import com.otilm.api.model.core.connector.ConnectorStatus;
import com.otilm.api.testsupport.ValidatorFixture;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.otilm.api.model.connector.cryptography.v2.utils.CryptographyDtoFixtures.validMetadata;
import static com.otilm.api.model.connector.cryptography.v2.utils.CryptographyDtoFixtures.withValidTokenProfileScope;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KeyApiClientTest {

    private static final String BASE_PATH = "/v2/cryptographyProvider/keys";
    private static final String CREATE_ATTRIBUTES_PATH = BASE_PATH + "/create/attributes";
    private static final String CREATE_STATUS_PATH = BASE_PATH + "/create/status";
    private static final String CREATE_CANCEL_PATH = BASE_PATH + "/create/cancel";
    private static final String DESTROY_PATH = BASE_PATH + "/destroy";
    private static final String DESTROY_STATUS_PATH = BASE_PATH + "/destroy/status";
    private static final String DESTROY_CANCEL_PATH = BASE_PATH + "/destroy/cancel";
    private static final String ATTRIBUTE_NAME = "createKeyAttribute";
    private static final String METADATA_NAME = "provider handle";
    private static final String KEY_CREATION_ID = "key-creation-1";
    private static final String VALID_ATTRIBUTE_LIST_JSON = """
            [
              {
                "uuid": "11111111-1111-1111-1111-111111111111",
                "name": "createKeyAttribute",
                "type": "data",
                "contentType": "string",
                "version": 2
              }
            ]
            """;
    private static final String VALID_METADATA_JSON = """
            {
              "uuid": "00000000-0000-0000-0000-000000000001",
              "name": "provider handle",
              "type": "meta",
              "contentType": "string",
              "version": 2,
              "properties": {},
              "content": ["provider-key-1"]
            }
            """;

    @AutoClose
    private static final ValidatorFixture VALIDATORS = new ValidatorFixture();

    private KeyApiClient client;
    private ConnectorDto connector;
    private WireMockServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = new WireMockServer(options().dynamicPort());
        mockServer.start();
        WireMock.configureFor("localhost", mockServer.port());

        connector = new ConnectorDto();
        connector.setName("cryptography connector");
        connector.setUrl("http://localhost:" + mockServer.port());
        connector.setStatus(ConnectorStatus.CONNECTED);

        OperationResponseValidator responseValidator = new OperationResponseValidator(VALIDATORS.validator());
        client = new KeyApiClient(BaseApiClient.prepareWebClient(), null, responseValidator);
    }

    @AfterEach
    void tearDown() {
        mockServer.stop();
    }

    @Test
    void listCreateKeyAttributes_postsRequestAndReturnsAttributes() throws ConnectorException {
        // given
        stubJsonResponse(CREATE_ATTRIBUTES_PATH, HttpStatus.OK, VALID_ATTRIBUTE_LIST_JSON);

        // when
        List<BaseAttribute> result = client.listCreateKeyAttributes(connector, createKeyAttributesRequest());

        // then
        assertEquals(1, result.size());
        assertEquals(ATTRIBUTE_NAME, result.get(0).getName());
        verifyCreateAttributesRequest();
    }

    @Test
    void createKey_returnsSynchronousResponse() throws ConnectorException {
        // given
        String responseJson = """
                {
                  "keyRequestType": "secret",
                  "keyData": {"type":"Secret","algorithm":"RSA","length":2048},
                  "keyMeta": [%s]
                }
                """.formatted(VALID_METADATA_JSON);
        stubJsonResponse(BASE_PATH, HttpStatus.OK, responseJson);

        // when
        ResponseEntity<KeyCreationResponseV2Dto> result = client
                .createKey(connector, createKeyRequest(OperationExecutionMode.SYNCHRONOUS));

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        SecretKeyDataResponseV2Dto body = assertInstanceOf(SecretKeyDataResponseV2Dto.class, result.getBody());
        assertEquals(KeyAlgorithm.RSA, body.getKeyData().getAlgorithm());
        assertEquals(METADATA_NAME, body.getKeyMeta().get(0).getName());
        verifyCreateRequest(OperationExecutionMode.SYNCHRONOUS);
    }

    @Test
    void createKey_returnsAsynchronousResponse() throws ConnectorException {
        // given
        String responseJson = """
                {"keyRequestType":"secret","operationMeta":[%s]}
                """.formatted(VALID_METADATA_JSON);
        stubJsonResponse(BASE_PATH, HttpStatus.ACCEPTED, responseJson);

        // when
        ResponseEntity<KeyCreationResponseV2Dto> result = client
                .createKey(connector, createKeyRequest(OperationExecutionMode.ASYNCHRONOUS));

        // then
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(METADATA_NAME, result.getBody().getOperationMeta().get(0).getName());
        verifyCreateRequest(OperationExecutionMode.ASYNCHRONOUS);
    }

    @Test
    void createKey_rejectsInvalidResponse() {
        // given
        String synchronousResponseWithoutKeyData = """
                {"keyRequestType":"secret"}
                """;
        stubJsonResponse(BASE_PATH, HttpStatus.OK, synchronousResponseWithoutKeyData);

        // when
        Executable call = () -> client.createKey(connector, createKeyRequest(OperationExecutionMode.SYNCHRONOUS));

        // then
        assertValidationFailure(call);
    }

    @Test
    void createKey_rejectsMismatchedKeyRequestType() {
        // given
        String secretKeyResponse = """
                {
                  "keyRequestType": "secret",
                  "keyData": {"type":"Secret","algorithm":"RSA","length":2048},
                  "keyMeta": [%s]
                }
                """.formatted(VALID_METADATA_JSON);
        CreateKeyRequestV2Dto keyPairRequest = createKeyRequest(KeyRequestType.KEY_PAIR,
                OperationExecutionMode.SYNCHRONOUS);
        stubJsonResponse(BASE_PATH, HttpStatus.OK, secretKeyResponse);

        // when
        Executable call = () -> client.createKey(connector, keyPairRequest);

        // then
        assertValidationFailure(call);
    }

    @Test
    void getCreateKeyStatus_postsRequestAndReturnsStatus() throws ConnectorException {
        // given
        String responseJson = """
                {"keyRequestType":"secret","status":"inProgress"}
                """;
        stubJsonResponse(CREATE_STATUS_PATH, HttpStatus.OK, responseJson);

        // when
        KeyCreationStatusResponseV2Dto result = client.getCreateKeyStatus(connector, keyOperationRequest());

        // then
        assertInstanceOf(SecretKeyOperationStatusResponseV2Dto.class, result);
        assertEquals(OperationStatus.IN_PROGRESS, result.getStatus());
        verifyOperationRequest(CREATE_STATUS_PATH);
    }

    @Test
    void getCreateKeyStatus_rejectsInvalidResponse() {
        // given
        String responseWithoutStatus = """
                {"keyRequestType":"secret"}
                """;
        stubJsonResponse(CREATE_STATUS_PATH, HttpStatus.OK, responseWithoutStatus);

        // when
        Executable call = () -> client.getCreateKeyStatus(connector, keyOperationRequest());

        // then
        assertValidationFailure(call);
    }

    @Test
    void cancelCreateKey_postsRequestAndPreservesStatus() throws ConnectorException {
        // given
        HttpStatus connectorStatus = HttpStatus.NO_CONTENT;
        stubBodilessResponse(CREATE_CANCEL_PATH, connectorStatus);

        // when
        ResponseEntity<Void> result = client.cancelCreateKey(connector, keyOperationRequest());

        // then
        assertEquals(connectorStatus, result.getStatusCode());
        verifyOperationRequest(CREATE_CANCEL_PATH);
    }

    @Test
    void destroyKey_returnsSynchronousResponse() throws ConnectorException {
        // given
        stubBodilessResponse(DESTROY_PATH, HttpStatus.OK);

        // when
        ResponseEntity<KeyOperationResponseV2Dto> result = client
                .destroyKey(connector, destroyKeyRequest(OperationExecutionMode.SYNCHRONOUS));

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNull(result.getBody());
        verifyDestroyRequest(OperationExecutionMode.SYNCHRONOUS);
    }

    @Test
    void destroyKey_returnsAsynchronousResponse() throws ConnectorException {
        // given
        String responseJson = """
                {"operationMeta":[%s]}
                """.formatted(VALID_METADATA_JSON);
        stubJsonResponse(DESTROY_PATH, HttpStatus.ACCEPTED, responseJson);

        // when
        ResponseEntity<KeyOperationResponseV2Dto> result = client
                .destroyKey(connector, destroyKeyRequest(OperationExecutionMode.ASYNCHRONOUS));

        // then
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(METADATA_NAME, result.getBody().getOperationMeta().get(0).getName());
        verifyDestroyRequest(OperationExecutionMode.ASYNCHRONOUS);
    }

    @Test
    void destroyKey_rejectsInvalidResponse() {
        // given
        String asynchronousResponseWithoutOperationMetadata = "{}";
        stubJsonResponse(DESTROY_PATH, HttpStatus.ACCEPTED, asynchronousResponseWithoutOperationMetadata);

        // when
        Executable call = () -> client.destroyKey(connector, destroyKeyRequest(OperationExecutionMode.ASYNCHRONOUS));

        // then
        assertValidationFailure(call);
    }

    @Test
    void getDestroyKeyStatus_postsRequestAndReturnsStatus() throws ConnectorException {
        // given
        String responseJson = """
                {"status":"inProgress"}
                """;
        stubJsonResponse(DESTROY_STATUS_PATH, HttpStatus.OK, responseJson);

        // when
        KeyOperationStatusResponseV2Dto result = client.getDestroyKeyStatus(connector, keyOperationRequest());

        // then
        assertInstanceOf(KeyDestructionStatusResponseV2Dto.class, result);
        assertEquals(OperationStatus.IN_PROGRESS, result.getStatus());
        verifyOperationRequest(DESTROY_STATUS_PATH);
    }

    @Test
    void getDestroyKeyStatus_rejectsInvalidResponse() {
        // given
        String responseWithoutStatus = "{}";
        stubJsonResponse(DESTROY_STATUS_PATH, HttpStatus.OK, responseWithoutStatus);

        // when
        Executable call = () -> client.getDestroyKeyStatus(connector, keyOperationRequest());

        // then
        assertValidationFailure(call);
    }

    @Test
    void cancelDestroyKey_postsRequestAndPreservesStatus() throws ConnectorException {
        // given
        HttpStatus connectorStatus = HttpStatus.NO_CONTENT;
        stubBodilessResponse(DESTROY_CANCEL_PATH, connectorStatus);

        // when
        ResponseEntity<Void> result = client.cancelDestroyKey(connector, keyOperationRequest());

        // then
        assertEquals(connectorStatus, result.getStatusCode());
        verifyOperationRequest(DESTROY_CANCEL_PATH);
    }

    @Test
    void createKey_propagatesConnectorHttpError() {
        // given
        HttpStatus connectorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String connectorError = "connector failure";
        mockServer
                .stubFor(WireMock
                        .post(BASE_PATH)
                        .willReturn(WireMock.aResponse().withStatus(connectorStatus.value()).withBody(connectorError)));

        // when
        Executable call = () -> client.createKey(connector, createKeyRequest(OperationExecutionMode.SYNCHRONOUS));

        // then
        ConnectorServerException exception = assertThrows(ConnectorServerException.class, call);
        assertEquals(connectorStatus, exception.getHttpStatus());
        assertSame(connector, exception.getConnector());
    }

    private void verifyCreateAttributesRequest() {
        RequestPatternBuilder request = tokenProfileRequest(CREATE_ATTRIBUTES_PATH)
                .withRequestBody(WireMock.matchingJsonPath("$.keyRequestType", WireMock.equalTo("secret")));
        mockServer.verify(request);
    }

    private void verifyCreateRequest(OperationExecutionMode mode) {
        RequestPatternBuilder request = tokenProfileRequest(BASE_PATH)
                .withRequestBody(WireMock.matchingJsonPath("$.keyRequestType", WireMock.equalTo("secret")))
                .withRequestBody(WireMock.matchingJsonPath("$.executionMode", WireMock.equalTo(mode.getCode())))
                .withRequestBody(WireMock.matchingJsonPath("$.keyCreationId", WireMock.equalTo(KEY_CREATION_ID)))
                .withRequestBody(WireMock.equalToJson("""
                        {"createKeyAttributes": []}
                        """, true, true));
        mockServer.verify(request);
    }

    private void verifyDestroyRequest(OperationExecutionMode mode) {
        RequestPatternBuilder request = tokenProfileRequest(DESTROY_PATH)
                .withRequestBody(WireMock.matchingJsonPath("$.executionMode", WireMock.equalTo(mode.getCode())))
                .withRequestBody(WireMock.matchingJsonPath("$.keyMeta[0].name", WireMock.equalTo(METADATA_NAME)));
        mockServer.verify(request);
    }

    private void verifyOperationRequest(String path) {
        RequestPatternBuilder request = tokenProfileRequest(path)
                .withRequestBody(WireMock.matchingJsonPath("$.operationMeta[0].name", WireMock.equalTo(METADATA_NAME)));
        mockServer.verify(request);
    }

    private RequestPatternBuilder tokenProfileRequest(String path) {
        return WireMock.postRequestedFor(WireMock.urlEqualTo(path)).withRequestBody(WireMock.equalToJson("""
                {
                  "tokenAttributes": [],
                  "tokenProfileAttributes": [],
                  "keyUsages": ["sign"]
                }
                """, true, true));
    }

    private void stubJsonResponse(String path, HttpStatus status, String body) {
        mockServer
                .stubFor(WireMock
                        .post(path)
                        .willReturn(WireMock
                                .aResponse()
                                .withStatus(status.value())
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(body)));
    }

    private void stubBodilessResponse(String path, HttpStatus status) {
        mockServer.stubFor(WireMock.post(path).willReturn(WireMock.aResponse().withStatus(status.value())));
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

    private static KeyOperationRequestV2Dto keyOperationRequest() {
        KeyOperationRequestV2Dto request = withValidTokenProfileScope(new KeyOperationRequestV2Dto());
        request.setOperationMeta(validMetadata());
        return request;
    }
}
