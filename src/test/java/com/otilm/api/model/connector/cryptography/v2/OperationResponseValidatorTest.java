package com.otilm.api.model.connector.cryptography.v2;

import com.otilm.api.model.client.cryptography.key.KeyRequestType;
import com.otilm.api.model.connector.common.v2.OperationExecutionMode;
import com.otilm.api.model.connector.cryptography.v2.key.CreateKeyRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyCreationResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyPairDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.SecretKeyDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.SignDataRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.SignDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.data.SignatureDataV2Dto;
import com.otilm.api.testsupport.ValidatorFixture;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.otilm.api.model.connector.cryptography.v2.utils.CryptographyDtoFixtures.validMetadata;
import static com.otilm.api.model.connector.cryptography.v2.utils.CryptographyDtoFixtures.validPrivateKeyDataResponse;
import static com.otilm.api.model.connector.cryptography.v2.utils.CryptographyDtoFixtures.validPublicKeyDataResponse;
import static com.otilm.api.model.connector.cryptography.v2.utils.CryptographyDtoFixtures.validSecretKeyDataResponse;
import static com.otilm.api.model.connector.cryptography.v2.utils.CryptographyDtoFixtures.withValidTokenProfileScope;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Named.named;

class OperationResponseValidatorTest {

    @AutoClose
    private static final ValidatorFixture VALIDATORS = new ValidatorFixture();
    private static final OperationResponseValidator VALIDATOR = new OperationResponseValidator(VALIDATORS.validator());

    @ParameterizedTest(name = "{0}")
    @MethodSource("validCreateKeyResponses")
    void validateCreateKey_acceptsValidResponse_forExecutionMode(CreateKeyCase testCase) {
        // given
        CreateKeyRequestV2Dto request = createKeyRequest(KeyRequestType.SECRET, testCase.mode());
        ResponseEntity<? extends KeyCreationResponseV2Dto> response = testCase.response();

        // when
        OperationValidationResult result = VALIDATOR.validateCreateKey(request, response);

        // then
        assertValid(result);
    }

    @Test
    void validateCreateKey_rejectsMissingRequest() {
        // given
        ResponseEntity<SecretKeyDataResponseV2Dto> response = synchronousCreateKeyResponse();

        // when
        OperationValidationResult result = VALIDATOR.validateCreateKey(null, response);

        // then
        assertInvalid(result);
    }

    @Test
    void validateCreateKey_rejectsMissingExecutionMode() {
        // given
        CreateKeyRequestV2Dto request = createKeyRequest(KeyRequestType.SECRET, null);
        ResponseEntity<SecretKeyDataResponseV2Dto> response = synchronousCreateKeyResponse();

        // when
        OperationValidationResult result = VALIDATOR.validateCreateKey(request, response);

        // then
        assertInvalid(result);
    }

    @Test
    void validateCreateKey_rejectsMissingKeyRequestType() {
        // given
        CreateKeyRequestV2Dto request = createKeyRequest(null, OperationExecutionMode.SYNCHRONOUS);
        ResponseEntity<SecretKeyDataResponseV2Dto> response = synchronousCreateKeyResponse();

        // when
        OperationValidationResult result = VALIDATOR.validateCreateKey(request, response);

        // then
        assertInvalid(result);
    }

    @Test
    void validateCreateKey_rejectsMissingResponse() {
        // given
        CreateKeyRequestV2Dto request = createKeyRequest(KeyRequestType.SECRET, OperationExecutionMode.SYNCHRONOUS);

        // when
        OperationValidationResult result = VALIDATOR.validateCreateKey(request, null);

        // then
        assertInvalid(result);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("createKeyResponsesWithContradictingStatus")
    void validateCreateKey_rejectsStatusContradictingExecutionMode(CreateKeyCase testCase) {
        // given
        CreateKeyRequestV2Dto request = createKeyRequest(KeyRequestType.SECRET, testCase.mode());
        ResponseEntity<? extends KeyCreationResponseV2Dto> response = testCase.response();

        // when
        OperationValidationResult result = VALIDATOR.validateCreateKey(request, response);

        // then
        assertInvalid(result);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("createKeyResponsesWithoutBody")
    void validateCreateKey_rejectsMissingBody_forExecutionMode(CreateKeyCase testCase) {
        // given
        CreateKeyRequestV2Dto request = createKeyRequest(KeyRequestType.SECRET, testCase.mode());
        ResponseEntity<? extends KeyCreationResponseV2Dto> response = testCase.response();

        // when
        OperationValidationResult result = VALIDATOR.validateCreateKey(request, response);

        // then
        assertInvalid(result);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("createKeyBodiesContradictingExecutionMode")
    void validateCreateKey_rejectsBodyContradictingExecutionMode(CreateKeyCase testCase) {
        // given
        CreateKeyRequestV2Dto request = createKeyRequest(KeyRequestType.SECRET, testCase.mode());
        ResponseEntity<? extends KeyCreationResponseV2Dto> response = testCase.response();

        // when
        OperationValidationResult result = VALIDATOR.validateCreateKey(request, response);

        // then
        assertInvalid(result);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("createKeyResponsesWithMismatchedKeyRequestType")
    void validateCreateKey_rejectsMismatchedKeyRequestType(CreateKeyTypeMismatchCase testCase) {
        // given
        CreateKeyRequestV2Dto request = createKeyRequest(testCase.requestType(), testCase.mode());
        ResponseEntity<? extends KeyCreationResponseV2Dto> response = testCase.response();

        // when
        OperationValidationResult result = VALIDATOR.validateCreateKey(request, response);

        // then
        assertInvalid(result);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("validDestroyKeyResponses")
    void validateDestroy_acceptsValidResponse_forExecutionMode(DestroyKeyCase testCase) {
        // given
        OperationExecutionMode mode = testCase.mode();
        ResponseEntity<KeyOperationResponseV2Dto> response = testCase.response();

        // when
        OperationValidationResult result = VALIDATOR.validateDestroy(mode, response);

        // then
        assertValid(result);
    }

    @Test
    void validateDestroy_rejectsMissingExecutionMode() {
        // given
        ResponseEntity<KeyOperationResponseV2Dto> response = ResponseEntity.ok().build();

        // when
        OperationValidationResult result = VALIDATOR.validateDestroy(null, response);

        // then
        assertInvalid(result);
    }

    @Test
    void validateDestroy_rejectsMissingResponse() {
        // given
        OperationExecutionMode mode = OperationExecutionMode.SYNCHRONOUS;

        // when
        OperationValidationResult result = VALIDATOR.validateDestroy(mode, null);

        // then
        assertInvalid(result);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("destroyKeyResponsesWithContradictingStatus")
    void validateDestroy_rejectsStatusContradictingExecutionMode(DestroyKeyCase testCase) {
        // given
        OperationExecutionMode mode = testCase.mode();
        ResponseEntity<KeyOperationResponseV2Dto> response = testCase.response();

        // when
        OperationValidationResult result = VALIDATOR.validateDestroy(mode, response);

        // then
        assertInvalid(result);
    }

    @Test
    void validateDestroy_rejectsBody_forSynchronousExecution() {
        // given
        OperationExecutionMode mode = OperationExecutionMode.SYNCHRONOUS;
        KeyOperationResponseV2Dto body = new KeyOperationResponseV2Dto();
        ResponseEntity<KeyOperationResponseV2Dto> response = ResponseEntity.ok(body);

        // when
        OperationValidationResult result = VALIDATOR.validateDestroy(mode, response);

        // then
        assertInvalid(result);
    }

    @Test
    void validateDestroy_rejectsMissingBody_forAsynchronousExecution() {
        // given
        OperationExecutionMode mode = OperationExecutionMode.ASYNCHRONOUS;
        ResponseEntity<KeyOperationResponseV2Dto> response = ResponseEntity.accepted().build();

        // when
        OperationValidationResult result = VALIDATOR.validateDestroy(mode, response);

        // then
        assertInvalid(result);
    }

    @Test
    void validateDestroy_rejectsInvalidAsynchronousOperationBody() {
        // given
        OperationExecutionMode mode = OperationExecutionMode.ASYNCHRONOUS;
        KeyOperationResponseV2Dto bodyWithoutOperationMetadata = new KeyOperationResponseV2Dto();
        ResponseEntity<KeyOperationResponseV2Dto> response = ResponseEntity
                .accepted()
                .body(bodyWithoutOperationMetadata);

        // when
        OperationValidationResult result = VALIDATOR.validateDestroy(mode, response);

        // then
        assertInvalid(result);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("validSignResponses")
    void validateSign_acceptsValidResponse_forExecutionMode(SignCase testCase) {
        // given
        SignDataRequestV2Dto request = testCase.request();
        ResponseEntity<SignDataResponseV2Dto> response = testCase.response();

        // when
        OperationValidationResult result = VALIDATOR.validateSign(request, response);

        // then
        assertValid(result);
    }

    @Test
    void validateSign_rejectsMissingRequest() {
        // given
        ResponseEntity<SignDataResponseV2Dto> response = synchronousSignResponse("item-1");

        // when
        OperationValidationResult result = VALIDATOR.validateSign(null, response);

        // then
        assertInvalid(result);
    }

    @Test
    void validateSign_rejectsMissingExecutionMode() {
        // given
        SignDataRequestV2Dto request = signRequest(OperationExecutionMode.SYNCHRONOUS, "item-1");
        request.setExecutionMode(null);
        ResponseEntity<SignDataResponseV2Dto> response = synchronousSignResponse("item-1");

        // when
        OperationValidationResult result = VALIDATOR.validateSign(request, response);

        // then
        assertInvalid(result);
    }

    @Test
    void validateSign_rejectsMissingResponse() {
        // given
        SignDataRequestV2Dto request = signRequest(OperationExecutionMode.SYNCHRONOUS, "item-1");

        // when
        OperationValidationResult result = VALIDATOR.validateSign(request, null);

        // then
        assertInvalid(result);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("signResponsesWithContradictingStatus")
    void validateSign_rejectsStatusContradictingExecutionMode(SignCase testCase) {
        // given
        SignDataRequestV2Dto request = testCase.request();
        ResponseEntity<SignDataResponseV2Dto> response = testCase.response();

        // when
        OperationValidationResult result = VALIDATOR.validateSign(request, response);

        // then
        assertInvalid(result);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("signResponsesWithoutBody")
    void validateSign_rejectsMissingBody_forExecutionMode(SignCase testCase) {
        // given
        SignDataRequestV2Dto request = testCase.request();
        ResponseEntity<SignDataResponseV2Dto> response = testCase.response();

        // when
        OperationValidationResult result = VALIDATOR.validateSign(request, response);

        // then
        assertInvalid(result);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("signBodiesContradictingExecutionMode")
    void validateSign_rejectsBodyContradictingExecutionMode(SignCase testCase) {
        // given
        SignDataRequestV2Dto request = testCase.request();
        ResponseEntity<SignDataResponseV2Dto> response = testCase.response();

        // when
        OperationValidationResult result = VALIDATOR.validateSign(request, response);

        // then
        assertInvalid(result);
    }

    @Test
    void validateSign_acceptsMatchingIdentifiers_inDifferentOrder() {
        // given
        String firstIdentifier = "item-1";
        String secondIdentifier = "item-2";
        SignDataRequestV2Dto request = signRequest(OperationExecutionMode.SYNCHRONOUS, firstIdentifier,
                secondIdentifier);
        ResponseEntity<SignDataResponseV2Dto> response = synchronousSignResponse(secondIdentifier, firstIdentifier);

        // when
        OperationValidationResult result = VALIDATOR.validateSign(request, response);

        // then
        assertValid(result);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("signResponsesWithDifferentIdentifierSets")
    void validateSign_rejectsDifferentIdentifierSets(SignCase testCase) {
        // given
        SignDataRequestV2Dto request = testCase.request();
        ResponseEntity<SignDataResponseV2Dto> response = testCase.response();

        // when
        OperationValidationResult result = VALIDATOR.validateSign(request, response);

        // then
        assertInvalid(result);
    }

    @Test
    void validateSign_rejectsMissingRequestData_forSynchronousExecution() {
        // given
        String identifier = "item-1";
        SignDataRequestV2Dto request = signRequest(OperationExecutionMode.SYNCHRONOUS, identifier);
        request.setData(null);
        ResponseEntity<SignDataResponseV2Dto> response = synchronousSignResponse(identifier);

        // when
        OperationValidationResult result = VALIDATOR.validateSign(request, response);

        // then
        assertInvalid(result);
    }

    static Stream<Named<CreateKeyCase>> validCreateKeyResponses() {
        return Stream
                .of(named("synchronous",
                        new CreateKeyCase(OperationExecutionMode.SYNCHRONOUS, synchronousCreateKeyResponse())),
                        named("asynchronous", new CreateKeyCase(OperationExecutionMode.ASYNCHRONOUS,
                                asynchronousCreateKeyResponse())));
    }

    static Stream<Named<CreateKeyCase>> createKeyResponsesWithContradictingStatus() {
        SecretKeyDataResponseV2Dto asynchronousBody = asynchronousCreateKeyBody();
        return Stream
                .of(named("synchronous receives 202",
                        new CreateKeyCase(OperationExecutionMode.SYNCHRONOUS,
                                ResponseEntity.accepted().body(validSecretKeyDataResponse()))),
                        named("asynchronous receives 200", new CreateKeyCase(OperationExecutionMode.ASYNCHRONOUS,
                                ResponseEntity.ok(asynchronousBody))));
    }

    static Stream<Named<CreateKeyCase>> createKeyResponsesWithoutBody() {
        return Stream
                .of(named("synchronous",
                        new CreateKeyCase(OperationExecutionMode.SYNCHRONOUS,
                                ResponseEntity.status(HttpStatus.OK).build())),
                        named("asynchronous", new CreateKeyCase(OperationExecutionMode.ASYNCHRONOUS,
                                ResponseEntity.status(HttpStatus.ACCEPTED).build())));
    }

    static Stream<Named<CreateKeyCase>> createKeyBodiesContradictingExecutionMode() {
        SecretKeyDataResponseV2Dto synchronousBodyWithOperationMetadata = validSecretKeyDataResponse();
        synchronousBodyWithOperationMetadata.setOperationMeta(validMetadata());
        SecretKeyDataResponseV2Dto asynchronousBodyWithKeyData = validSecretKeyDataResponse();
        asynchronousBodyWithKeyData.setOperationMeta(validMetadata());
        return Stream
                .of(named("synchronous body contains operation metadata",
                        new CreateKeyCase(OperationExecutionMode.SYNCHRONOUS,
                                ResponseEntity.ok(synchronousBodyWithOperationMetadata))),
                        named("asynchronous body contains synchronous key data",
                                new CreateKeyCase(OperationExecutionMode.ASYNCHRONOUS,
                                        ResponseEntity.accepted().body(asynchronousBodyWithKeyData))));
    }

    static Stream<Named<CreateKeyTypeMismatchCase>> createKeyResponsesWithMismatchedKeyRequestType() {
        return Stream
                .of(named("synchronous secret request receives key pair",
                        new CreateKeyTypeMismatchCase(KeyRequestType.SECRET, OperationExecutionMode.SYNCHRONOUS,
                                ResponseEntity.ok(synchronousCreateKeyPairBody()))),
                        named("asynchronous secret request receives key pair",
                                new CreateKeyTypeMismatchCase(KeyRequestType.SECRET,
                                        OperationExecutionMode.ASYNCHRONOUS,
                                        ResponseEntity.accepted().body(asynchronousCreateKeyPairBody()))),
                        named("synchronous key-pair request receives secret key",
                                new CreateKeyTypeMismatchCase(KeyRequestType.KEY_PAIR,
                                        OperationExecutionMode.SYNCHRONOUS, synchronousCreateKeyResponse())),
                        named("asynchronous key-pair request receives secret key",
                                new CreateKeyTypeMismatchCase(KeyRequestType.KEY_PAIR,
                                        OperationExecutionMode.ASYNCHRONOUS, asynchronousCreateKeyResponse())));
    }

    static Stream<Named<DestroyKeyCase>> validDestroyKeyResponses() {
        return Stream
                .of(named("synchronous",
                        new DestroyKeyCase(OperationExecutionMode.SYNCHRONOUS, ResponseEntity.ok().build())),
                        named("asynchronous", new DestroyKeyCase(OperationExecutionMode.ASYNCHRONOUS,
                                asynchronousDestroyKeyResponse())));
    }

    static Stream<Named<DestroyKeyCase>> destroyKeyResponsesWithContradictingStatus() {
        return Stream
                .of(named("synchronous receives 202",
                        new DestroyKeyCase(OperationExecutionMode.SYNCHRONOUS, ResponseEntity.accepted().build())),
                        named("asynchronous receives 200", new DestroyKeyCase(OperationExecutionMode.ASYNCHRONOUS,
                                ResponseEntity.ok(asynchronousDestroyKeyBody()))));
    }

    static Stream<Named<SignCase>> validSignResponses() {
        String identifier = "item-1";
        return Stream
                .of(named("synchronous",
                        new SignCase(signRequest(OperationExecutionMode.SYNCHRONOUS, identifier),
                                synchronousSignResponse(identifier))),
                        named("asynchronous", new SignCase(signRequest(OperationExecutionMode.ASYNCHRONOUS, identifier),
                                asynchronousSignResponse())));
    }

    static Stream<Named<SignCase>> signResponsesWithContradictingStatus() {
        String identifier = "item-1";
        SignDataResponseV2Dto synchronousBody = synchronousSignBody(identifier);
        SignDataResponseV2Dto asynchronousBody = asynchronousSignBody();
        return Stream
                .of(named("synchronous receives 202",
                        new SignCase(signRequest(OperationExecutionMode.SYNCHRONOUS, identifier),
                                ResponseEntity.accepted().body(synchronousBody))),
                        named("asynchronous receives 200",
                                new SignCase(signRequest(OperationExecutionMode.ASYNCHRONOUS, identifier),
                                        ResponseEntity.ok(asynchronousBody))));
    }

    static Stream<Named<SignCase>> signResponsesWithoutBody() {
        String identifier = "item-1";
        return Stream
                .of(named("synchronous",
                        new SignCase(signRequest(OperationExecutionMode.SYNCHRONOUS, identifier),
                                ResponseEntity.status(HttpStatus.OK).build())),
                        named("asynchronous", new SignCase(signRequest(OperationExecutionMode.ASYNCHRONOUS, identifier),
                                ResponseEntity.status(HttpStatus.ACCEPTED).build())));
    }

    static Stream<Named<SignCase>> signBodiesContradictingExecutionMode() {
        String identifier = "item-1";
        SignDataResponseV2Dto synchronousBodyWithOperationMetadata = synchronousSignBody(identifier);
        synchronousBodyWithOperationMetadata.setSignOperationMeta(validMetadata());
        SignDataResponseV2Dto asynchronousBodyWithSignatures = asynchronousSignBody();
        asynchronousBodyWithSignatures.setSignatures(List.of(signatureItem(identifier)));
        return Stream
                .of(named("synchronous body contains operation metadata",
                        new SignCase(signRequest(OperationExecutionMode.SYNCHRONOUS, identifier),
                                ResponseEntity.ok(synchronousBodyWithOperationMetadata))),
                        named("asynchronous body contains signatures",
                                new SignCase(signRequest(OperationExecutionMode.ASYNCHRONOUS, identifier),
                                        ResponseEntity.accepted().body(asynchronousBodyWithSignatures))));
    }

    static Stream<Named<SignCase>> signResponsesWithDifferentIdentifierSets() {
        String firstIdentifier = "item-1";
        String secondIdentifier = "item-2";
        String replacementIdentifier = "item-3";
        return Stream
                .of(named("response is missing an identifier",
                        new SignCase(signRequest(OperationExecutionMode.SYNCHRONOUS, firstIdentifier, secondIdentifier),
                                synchronousSignResponse(firstIdentifier))),
                        named("response has an additional identifier",
                                new SignCase(signRequest(OperationExecutionMode.SYNCHRONOUS, firstIdentifier),
                                        synchronousSignResponse(firstIdentifier, secondIdentifier))),
                        named("response substitutes an identifier",
                                new SignCase(
                                        signRequest(OperationExecutionMode.SYNCHRONOUS, firstIdentifier,
                                                secondIdentifier),
                                        synchronousSignResponse(firstIdentifier, replacementIdentifier))));
    }

    private static ResponseEntity<SecretKeyDataResponseV2Dto> synchronousCreateKeyResponse() {
        return ResponseEntity.ok(validSecretKeyDataResponse());
    }

    private static ResponseEntity<SecretKeyDataResponseV2Dto> asynchronousCreateKeyResponse() {
        return ResponseEntity.accepted().body(asynchronousCreateKeyBody());
    }

    private static SecretKeyDataResponseV2Dto asynchronousCreateKeyBody() {
        SecretKeyDataResponseV2Dto body = new SecretKeyDataResponseV2Dto();
        body.setOperationMeta(validMetadata());
        return body;
    }

    private static KeyPairDataResponseV2Dto synchronousCreateKeyPairBody() {
        KeyPairDataResponseV2Dto body = new KeyPairDataResponseV2Dto();
        body.setPublicKeyData(validPublicKeyDataResponse());
        body.setPrivateKeyData(validPrivateKeyDataResponse());
        body.setKeyPairMeta(validMetadata());
        return body;
    }

    private static KeyPairDataResponseV2Dto asynchronousCreateKeyPairBody() {
        KeyPairDataResponseV2Dto body = new KeyPairDataResponseV2Dto();
        body.setOperationMeta(validMetadata());
        return body;
    }

    private static CreateKeyRequestV2Dto createKeyRequest(KeyRequestType keyRequestType,
            OperationExecutionMode executionMode) {
        CreateKeyRequestV2Dto request = new CreateKeyRequestV2Dto();
        request.setKeyRequestType(keyRequestType);
        request.setExecutionMode(executionMode);
        return request;
    }

    private static ResponseEntity<KeyOperationResponseV2Dto> asynchronousDestroyKeyResponse() {
        return ResponseEntity.accepted().body(asynchronousDestroyKeyBody());
    }

    private static KeyOperationResponseV2Dto asynchronousDestroyKeyBody() {
        KeyOperationResponseV2Dto body = new KeyOperationResponseV2Dto();
        body.setOperationMeta(validMetadata());
        return body;
    }

    private static SignDataRequestV2Dto signRequest(OperationExecutionMode executionMode, String... identifiers) {
        SignDataRequestV2Dto request = withValidTokenProfileScope(new SignDataRequestV2Dto());
        request.setKeyMeta(validMetadata());
        request.setExecutionMode(executionMode);
        request.setSignatureAttributes(List.of());
        request.setData(Stream.of(identifiers).map(OperationResponseValidatorTest::signatureItem).toList());
        return request;
    }

    private static ResponseEntity<SignDataResponseV2Dto> synchronousSignResponse(String... identifiers) {
        return ResponseEntity.ok(synchronousSignBody(identifiers));
    }

    private static SignDataResponseV2Dto synchronousSignBody(String... identifiers) {
        SignDataResponseV2Dto response = new SignDataResponseV2Dto();
        response.setSignatures(Stream.of(identifiers).map(OperationResponseValidatorTest::signatureItem).toList());
        return response;
    }

    private static ResponseEntity<SignDataResponseV2Dto> asynchronousSignResponse() {
        return ResponseEntity.accepted().body(asynchronousSignBody());
    }

    private static SignDataResponseV2Dto asynchronousSignBody() {
        SignDataResponseV2Dto response = new SignDataResponseV2Dto();
        response.setSignOperationMeta(validMetadata());
        return response;
    }

    private static SignatureDataV2Dto signatureItem(String identifier) {
        byte[] data = {1};
        return new SignatureDataV2Dto(data, identifier);
    }

    private static void assertValid(OperationValidationResult result) {
        assertTrue(result.isValid());
    }

    private static void assertInvalid(OperationValidationResult result) {
        assertFalse(result.isValid());
        assertNotNull(result.getCause());
    }

    private record CreateKeyCase(OperationExecutionMode mode,
            ResponseEntity<? extends KeyCreationResponseV2Dto> response) {
    }

    private record CreateKeyTypeMismatchCase(KeyRequestType requestType, OperationExecutionMode mode,
            ResponseEntity<? extends KeyCreationResponseV2Dto> response) {
    }

    private record DestroyKeyCase(OperationExecutionMode mode, ResponseEntity<KeyOperationResponseV2Dto> response) {
    }

    private record SignCase(SignDataRequestV2Dto request, ResponseEntity<SignDataResponseV2Dto> response) {
    }
}
