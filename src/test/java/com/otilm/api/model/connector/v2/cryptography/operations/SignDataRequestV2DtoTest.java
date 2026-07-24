package com.otilm.api.model.connector.v2.cryptography.operations;

import com.otilm.api.model.connector.cryptography.v2.operations.SignDataRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.data.SignatureRequestDataV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.validation.UniqueSignatureIdentifiers;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.otilm.api.model.connector.v2.cryptography.RedactionTestUtils.sensitiveAttribute;
import static com.otilm.api.model.connector.v2.cryptography.ValidationTestUtils.assertViolation;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SignDataRequestV2DtoTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validation_rejectsDuplicateDataIdentifiers() {
        // given
        var duplicateIdentifier = "duplicate";
        var request = new SignDataRequestV2Dto();
        request.setData(List.of(item(duplicateIdentifier), item(duplicateIdentifier)));

        // when
        var violations = VALIDATOR.validate(request);

        // then
        assertViolation(violations, "data", UniqueSignatureIdentifiers.class);
    }

    @Test
    void validation_rejectsMissingRequiredFields() {
        // given
        var request = new SignDataRequestV2Dto();

        // when
        var violations = VALIDATOR.validate(request);

        // then
        assertViolation(violations, "executionMode", NotNull.class);
        assertViolation(violations, "signatureAttributes", NotNull.class);
        assertViolation(violations, "data", NotEmpty.class);
    }

    @Test
    void validation_rejectsNullAndInvalidDataItems() {
        // given
        var request = new SignDataRequestV2Dto();

        // when
        request.setData(Collections.singletonList(null));
        var nullItemViolations = VALIDATOR.validate(request);
        request.setData(List.of(new SignatureRequestDataV2Dto(null, " ")));
        var invalidItemViolations = VALIDATOR.validate(request);

        // then
        assertViolation(nullItemViolations, "data[0].<list element>", NotNull.class);
        assertViolation(invalidItemViolations, "data[0].data", NotNull.class);
        assertViolation(invalidItemViolations, "data[0].identifier", jakarta.validation.constraints.NotBlank.class);
    }

    @Test
    void toString_redactsScopedAttributesAndPayload() {
        // given
        var credentialMarker = "TOKEN-PIN-SECRET";
        var payloadMarker = "[101, 102, 103]";
        var request = new SignDataRequestV2Dto();
        request.setTokenAttributes(List.of(sensitiveAttribute(credentialMarker)));
        request.setTokenProfileAttributes(List.of(sensitiveAttribute(credentialMarker)));
        request.setSignatureAttributes(List.of(sensitiveAttribute(credentialMarker)));
        request.setData(List.of(new SignatureRequestDataV2Dto(new byte[]{101, 102, 103}, "item-1")));

        // when
        var representation = request.toString();

        // then
        assertFalse(representation.contains(credentialMarker));
        assertFalse(representation.contains(payloadMarker));
        assertTrue(representation.contains("identifier=item-1"));
    }

    private static SignatureRequestDataV2Dto item(String identifier) {
        return new SignatureRequestDataV2Dto(new byte[]{1}, identifier);
    }
}
