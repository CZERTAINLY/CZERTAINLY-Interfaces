package com.otilm.api.model.connector.v2.cryptography.operations;

import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.DecryptDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.EncryptDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.SignOperationStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.VerifyDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.data.CipherResponseDataV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.data.SignatureItemResultV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.data.VerificationResponseDataV2Dto;
import com.otilm.api.model.connector.cryptography.v2.token.TokenStatusResponseV2Dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.otilm.api.model.connector.v2.cryptography.ValidationTestUtils.assertViolation;
import static com.otilm.api.model.connector.v2.cryptography.ValidationTestUtils.validate;
import static org.junit.jupiter.api.Named.named;

class OperationResponseV2DtoTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("requiredListResponses")
    void validation_rejectsMissingEmptyAndNullResponseItems(ResponseListCase responseCase) {
        // given
        var response = responseCase.response();

        // when
        var missingList = validate(response);
        responseCase.setter().accept(List.of());
        var emptyList = validate(response);
        responseCase.setter().accept(Collections.singletonList(null));
        var nullItem = validate(response);

        // then
        assertViolation(missingList, responseCase.property(), NotEmpty.class);
        assertViolation(emptyList, responseCase.property(), NotEmpty.class);
        assertViolation(nullItem, responseCase.property() + "[0].<list element>", NotNull.class);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("nestedResponseItems")
    void validation_cascadesToNestedResponseItem(ResponseListCase responseCase) {
        // given
        var response = responseCase.response();
        responseCase.setter().accept(responseCase.invalidItems());

        // when
        var violations = validate(response);

        // then
        assertViolation(violations, responseCase.property() + "[0].identifier", NotBlank.class);
    }

    @Test
    void signOperationStatusValidation_requiresItemsAndCascadesFields() {
        // given
        var response = new SignOperationStatusResponseV2Dto();

        // when
        var missingItems = validate(response);
        response.setItems(Collections.singletonList(null));
        var nullItem = validate(response);
        response.setItems(List.of(new SignatureItemResultV2Dto(" ", null, null, null)));
        var invalidItem = validate(response);

        // then
        assertViolation(missingItems, "items", NotEmpty.class);
        assertViolation(nullItem, "items[0].<list element>", NotNull.class);
        assertViolation(invalidItem, "items[0].identifier", NotBlank.class);
        assertViolation(invalidItem, "items[0].status", NotNull.class);
    }

    @Test
    void statusResponses_requireStatus() {
        // given
        var keyStatus = new KeyOperationStatusResponseV2Dto();
        var tokenStatus = new TokenStatusResponseV2Dto();

        // when
        var keyViolations = validate(keyStatus);
        var tokenViolations = validate(tokenStatus);

        // then
        assertViolation(keyViolations, "status", NotNull.class);
        assertViolation(tokenViolations, "status", NotNull.class);
    }

    private static Stream<Named<ResponseListCase>> requiredListResponses() {
        var encrypted = new EncryptDataResponseV2Dto();
        var decrypted = new DecryptDataResponseV2Dto();
        var verified = new VerifyDataResponseV2Dto();
        return Stream.of(
                named("encrypt response", new ResponseListCase(encrypted, "encryptedData",
                        items -> encrypted.setEncryptedData(cast(items)), null)),
                named("decrypt response", new ResponseListCase(decrypted, "decryptedData",
                        items -> decrypted.setDecryptedData(cast(items)), null)),
                named("verify response", new ResponseListCase(verified, "verifications",
                        items -> verified.setVerifications(cast(items)), null)));
    }

    private static Stream<Named<ResponseListCase>> nestedResponseItems() {
        var encrypted = new EncryptDataResponseV2Dto();
        var decrypted = new DecryptDataResponseV2Dto();
        var verified = new VerifyDataResponseV2Dto();
        return Stream.of(
                named("encrypt response", new ResponseListCase(encrypted, "encryptedData",
                        items -> encrypted.setEncryptedData(cast(items)),
                        List.of(new CipherResponseDataV2Dto(new byte[]{1}, " ", null)))),
                named("decrypt response", new ResponseListCase(decrypted, "decryptedData",
                        items -> decrypted.setDecryptedData(cast(items)),
                        List.of(new CipherResponseDataV2Dto(new byte[]{1}, " ", null)))),
                named("verify response", new ResponseListCase(verified, "verifications",
                        items -> verified.setVerifications(cast(items)),
                        List.of(new VerificationResponseDataV2Dto(true, " ", null)))));
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> cast(List<?> items) {
        return (List<T>) items;
    }

    private record ResponseListCase(
            Object response,
            String property,
            Consumer<List<?>> setter,
            List<?> invalidItems) {
    }
}
