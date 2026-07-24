package com.otilm.api.model.connector.v2.cryptography.operations;

import com.otilm.api.model.connector.cryptography.v2.operations.VerifyDataRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.data.SignatureRequestDataV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.validation.UniqueSignatureIdentifiers;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.otilm.api.model.connector.v2.cryptography.ValidationTestUtils.assertViolation;

class VerifyDataRequestV2DtoTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validation_rejectsDuplicateDataIdentifiers() {
        // given
        var duplicateIdentifier = "duplicate";
        var request = validRequest();
        request.setData(List.of(item(duplicateIdentifier), item(duplicateIdentifier)));

        // when
        var violations = VALIDATOR.validate(request);

        // then
        assertViolation(violations, "data", UniqueSignatureIdentifiers.class);
    }

    @Test
    void validation_rejectsDuplicateSignatureIdentifiers() {
        // given
        var duplicateIdentifier = "duplicate";
        var request = validRequest();
        request.setSignatures(List.of(item(duplicateIdentifier), item(duplicateIdentifier)));

        // when
        var violations = VALIDATOR.validate(request);

        // then
        assertViolation(violations, "signatures", UniqueSignatureIdentifiers.class);
    }

    @Test
    void validation_rejectsMissingRequiredFields() {
        // given
        var request = new VerifyDataRequestV2Dto();

        // when
        var violations = VALIDATOR.validate(request);

        // then
        assertViolation(violations, "signatureAttributes", jakarta.validation.constraints.NotNull.class);
        assertViolation(violations, "data", jakarta.validation.constraints.NotEmpty.class);
        assertViolation(violations, "signatures", jakarta.validation.constraints.NotEmpty.class);
    }

    @Test
    void validation_rejectsNullAndInvalidItemsInBothCollections() {
        // given
        var request = validRequest();
        var invalidItem = new SignatureRequestDataV2Dto(null, " ");

        // when
        request.setData(Collections.singletonList(null));
        request.setSignatures(List.of(invalidItem));
        var violations = VALIDATOR.validate(request);

        // then
        assertViolation(violations, "data[0].<list element>", jakarta.validation.constraints.NotNull.class);
        assertViolation(violations, "signatures[0].data", jakarta.validation.constraints.NotNull.class);
        assertViolation(violations, "signatures[0].identifier", jakarta.validation.constraints.NotBlank.class);
    }

    private static VerifyDataRequestV2Dto validRequest() {
        var request = new VerifyDataRequestV2Dto();
        request.setData(List.of(item("data-item")));
        request.setSignatures(List.of(item("signature-item")));
        return request;
    }

    private static SignatureRequestDataV2Dto item(String identifier) {
        return new SignatureRequestDataV2Dto(new byte[]{1}, identifier);
    }

}
