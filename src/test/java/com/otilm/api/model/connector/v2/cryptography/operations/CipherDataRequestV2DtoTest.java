package com.otilm.api.model.connector.v2.cryptography.operations;

import com.otilm.api.model.connector.cryptography.v2.operations.CipherDataRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.data.CipherRequestDataV2Dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.otilm.api.model.connector.v2.cryptography.ValidationTestUtils.assertViolation;
import static com.otilm.api.model.connector.v2.cryptography.ValidationTestUtils.validate;

class CipherDataRequestV2DtoTest {

    @Test
    void validation_rejectsMissingAttributesAndData() {
        // given
        var request = new CipherDataRequestV2Dto();

        // when
        var violations = validate(request);

        // then
        assertViolation(violations, "cipherAttributes", NotNull.class);
        assertViolation(violations, "cipherData", NotEmpty.class);
    }

    @Test
    void validation_acceptsEmptyAttributes_butRejectsEmptyData() {
        // given
        var request = new CipherDataRequestV2Dto();
        request.setCipherAttributes(List.of());
        request.setCipherData(List.of());

        // when
        var violations = validate(request);

        // then
        assertViolation(violations, "cipherData", NotEmpty.class);
    }

    @Test
    void validation_rejectsNullAndInvalidNestedItems() {
        // given
        var request = new CipherDataRequestV2Dto();

        // when
        request.setCipherData(Collections.singletonList(null));
        var nullItemViolations = validate(request);
        request.setCipherData(List.of(new CipherRequestDataV2Dto(new byte[0], " ")));
        var invalidItemViolations = validate(request);

        // then
        assertViolation(nullItemViolations, "cipherData[0].<list element>", NotNull.class);
        assertViolation(invalidItemViolations, "cipherData[0].data", NotEmpty.class);
        assertViolation(invalidItemViolations, "cipherData[0].identifier", NotBlank.class);
    }
}
