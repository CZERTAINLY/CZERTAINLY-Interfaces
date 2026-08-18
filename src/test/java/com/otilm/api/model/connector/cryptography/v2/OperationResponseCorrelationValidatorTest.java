package com.otilm.api.model.connector.cryptography.v2;

import com.otilm.api.model.connector.cryptography.v2.operations.CipherDataRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.DecryptDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.EncryptDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.RandomDataRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.RandomDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.VerifyDataRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.VerifyDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.data.CipherDataV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.data.SignatureDataV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.data.VerificationResponseItemV2Dto;
import com.otilm.api.testsupport.ValidatorFixture;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Named.named;

class OperationResponseCorrelationValidatorTest {

    private static final byte[] DATA = {1};
    private static final String FIRST_IDENTIFIER = "item-1";
    private static final String SECOND_IDENTIFIER = "item-2";
    private static final String DIFFERENT_IDENTIFIER = "different-item";

    @AutoClose
    private static final ValidatorFixture VALIDATORS = new ValidatorFixture();
    private static final OperationResponseValidator VALIDATOR = new OperationResponseValidator(VALIDATORS.validator());

    @Test
    void validateEncrypt_acceptsMatchingIdentifiers_inDifferentOrder() {
        // given
        CipherDataRequestV2Dto request = cipherRequest(FIRST_IDENTIFIER, SECOND_IDENTIFIER);
        EncryptDataResponseV2Dto response = encryptResponse(SECOND_IDENTIFIER, FIRST_IDENTIFIER);

        // when
        OperationValidationResult result = VALIDATOR.validateEncrypt(request, response);

        // then
        assertValid(result);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("encryptResponsesWithDifferentIdentifierSets")
    void validateEncrypt_rejectsDifferentIdentifierSets(EncryptCorrelationCase testCase) {
        // given
        CipherDataRequestV2Dto request = cipherRequest(testCase.requestIdentifiers());
        EncryptDataResponseV2Dto response = encryptResponse(testCase.responseIdentifiers());

        // when
        OperationValidationResult result = VALIDATOR.validateEncrypt(request, response);

        // then
        assertInvalid(result);
    }

    @Test
    void validateDecrypt_acceptsMatchingIdentifiers_inDifferentOrder() {
        // given
        CipherDataRequestV2Dto request = cipherRequest(FIRST_IDENTIFIER, SECOND_IDENTIFIER);
        DecryptDataResponseV2Dto response = decryptResponse(SECOND_IDENTIFIER, FIRST_IDENTIFIER);

        // when
        OperationValidationResult result = VALIDATOR.validateDecrypt(request, response);

        // then
        assertValid(result);
    }

    @Test
    void validateDecrypt_rejectsSubstitutedIdentifier() {
        // given
        CipherDataRequestV2Dto request = cipherRequest(FIRST_IDENTIFIER);
        DecryptDataResponseV2Dto response = decryptResponse(DIFFERENT_IDENTIFIER);

        // when
        OperationValidationResult result = VALIDATOR.validateDecrypt(request, response);

        // then
        assertInvalid(result);
    }

    @Test
    void validateVerify_acceptsMatchingIdentifiers_inDifferentOrder() {
        // given
        VerifyDataRequestV2Dto request = verifyRequest(FIRST_IDENTIFIER, SECOND_IDENTIFIER);
        VerifyDataResponseV2Dto response = verifyResponse(SECOND_IDENTIFIER, FIRST_IDENTIFIER);

        // when
        OperationValidationResult result = VALIDATOR.validateVerify(request, response);

        // then
        assertValid(result);
    }

    @Test
    void validateVerify_rejectsSubstitutedIdentifier() {
        // given
        VerifyDataRequestV2Dto request = verifyRequest(FIRST_IDENTIFIER);
        VerifyDataResponseV2Dto response = verifyResponse(DIFFERENT_IDENTIFIER);

        // when
        OperationValidationResult result = VALIDATOR.validateVerify(request, response);

        // then
        assertInvalid(result);
    }

    @Test
    void validateRandom_acceptsRequestedLength() {
        // given
        int requestedLength = 2;
        RandomDataRequestV2Dto request = randomRequest(requestedLength);
        RandomDataResponseV2Dto response = randomResponse(requestedLength);

        // when
        OperationValidationResult result = VALIDATOR.validateRandom(request, response);

        // then
        assertValid(result);
    }

    @Test
    void validateRandom_rejectsShorterData() {
        // given
        int requestedLength = 2;
        int returnedLength = 1;
        RandomDataRequestV2Dto request = randomRequest(requestedLength);
        RandomDataResponseV2Dto response = randomResponse(returnedLength);

        // when
        OperationValidationResult result = VALIDATOR.validateRandom(request, response);

        // then
        assertInvalid(result);
    }

    @Test
    void validateRandom_rejectsLongerData() {
        // given
        int requestedLength = 2;
        int returnedLength = 3;
        RandomDataRequestV2Dto request = randomRequest(requestedLength);
        RandomDataResponseV2Dto response = randomResponse(returnedLength);

        // when
        OperationValidationResult result = VALIDATOR.validateRandom(request, response);

        // then
        assertInvalid(result);
    }

    static Stream<Named<EncryptCorrelationCase>> encryptResponsesWithDifferentIdentifierSets() {
        return Stream
                .of(named("response is missing an identifier",
                        new EncryptCorrelationCase(new String[]{FIRST_IDENTIFIER, SECOND_IDENTIFIER},
                                new String[]{FIRST_IDENTIFIER})),
                        named("response has an additional identifier",
                                new EncryptCorrelationCase(new String[]{FIRST_IDENTIFIER},
                                        new String[]{FIRST_IDENTIFIER, SECOND_IDENTIFIER})),
                        named("response substitutes an identifier",
                                new EncryptCorrelationCase(new String[]{FIRST_IDENTIFIER, SECOND_IDENTIFIER},
                                        new String[]{FIRST_IDENTIFIER, DIFFERENT_IDENTIFIER})));
    }

    private static CipherDataRequestV2Dto cipherRequest(String... identifiers) {
        CipherDataRequestV2Dto request = new CipherDataRequestV2Dto();
        request
                .setCipherData(
                        Stream.of(identifiers).map(OperationResponseCorrelationValidatorTest::cipherItem).toList());
        return request;
    }

    private static EncryptDataResponseV2Dto encryptResponse(String... identifiers) {
        EncryptDataResponseV2Dto response = new EncryptDataResponseV2Dto();
        response
                .setEncryptedData(
                        Stream.of(identifiers).map(OperationResponseCorrelationValidatorTest::cipherItem).toList());
        return response;
    }

    private static DecryptDataResponseV2Dto decryptResponse(String... identifiers) {
        DecryptDataResponseV2Dto response = new DecryptDataResponseV2Dto();
        response
                .setDecryptedData(
                        Stream.of(identifiers).map(OperationResponseCorrelationValidatorTest::cipherItem).toList());
        return response;
    }

    private static VerifyDataRequestV2Dto verifyRequest(String... identifiers) {
        List<SignatureDataV2Dto> data = Stream
                .of(identifiers)
                .map(identifier -> new SignatureDataV2Dto(DATA, identifier))
                .toList();
        VerifyDataRequestV2Dto request = new VerifyDataRequestV2Dto();
        request.setData(data);
        request.setSignatures(data);
        return request;
    }

    private static VerifyDataResponseV2Dto verifyResponse(String... identifiers) {
        VerifyDataResponseV2Dto response = new VerifyDataResponseV2Dto();
        response
                .setVerifications(Stream
                        .of(identifiers)
                        .map(identifier -> new VerificationResponseItemV2Dto(true, identifier, null))
                        .toList());
        return response;
    }

    private static RandomDataRequestV2Dto randomRequest(int length) {
        RandomDataRequestV2Dto request = new RandomDataRequestV2Dto();
        request.setLength(length);
        return request;
    }

    private static RandomDataResponseV2Dto randomResponse(int length) {
        RandomDataResponseV2Dto response = new RandomDataResponseV2Dto();
        response.setData(new byte[length]);
        return response;
    }

    private static CipherDataV2Dto cipherItem(String identifier) {
        return new CipherDataV2Dto(DATA, identifier);
    }

    private static void assertValid(OperationValidationResult result) {
        assertTrue(result.isValid());
    }

    private static void assertInvalid(OperationValidationResult result) {
        assertFalse(result.isValid());
        assertNotNull(result.getCause());
    }

    private record EncryptCorrelationCase(String[] requestIdentifiers, String[] responseIdentifiers) {
    }
}
