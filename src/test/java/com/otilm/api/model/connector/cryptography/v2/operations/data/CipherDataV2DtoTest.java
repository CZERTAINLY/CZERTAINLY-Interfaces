package com.otilm.api.model.connector.cryptography.v2.operations.data;

import com.otilm.api.testsupport.ValidatorFixture;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CipherDataV2DtoTest {

    @AutoClose
    private static final ValidatorFixture VALIDATORS = new ValidatorFixture();
    private static final Validator VALIDATOR = VALIDATORS.validator();

    @Test
    void validate_rejectsEmptyData() {
        // given
        CipherDataV2Dto item = new CipherDataV2Dto(new byte[0], "item-1");

        // when
        Set<ConstraintViolation<CipherDataV2Dto>> violations = VALIDATOR.validate(item);

        // then
        assertHasViolation(violations, "data", "data is required and must not be empty");
    }

    @Test
    void validate_rejectsBlankIdentifier() {
        // given
        CipherDataV2Dto item = new CipherDataV2Dto(new byte[]{1}, " ");

        // when
        Set<ConstraintViolation<CipherDataV2Dto>> violations = VALIDATOR.validate(item);

        // then
        assertHasViolation(violations, "identifier", "identifier is required");
    }

    @Test
    void validate_acceptsValidItem() {
        // given
        CipherDataV2Dto item = new CipherDataV2Dto(new byte[]{1}, "item-1");

        // when
        Set<ConstraintViolation<CipherDataV2Dto>> violations = VALIDATOR.validate(item);

        // then
        assertTrue(violations.isEmpty());
    }

    @Test
    void toString_redactsData() {
        // given
        String dataMarker = "[101, 102, 103]";
        String identifier = "item-1";
        CipherDataV2Dto item = new CipherDataV2Dto(new byte[]{101, 102, 103}, identifier);

        // when
        String representation = item.toString();

        // then
        assertFalse(representation.contains(dataMarker));
        assertTrue(representation.contains(identifier));
    }

    private static void assertHasViolation(Set<ConstraintViolation<CipherDataV2Dto>> violations, String path,
            String message) {
        assertTrue(violations
                .stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals(path)
                        && violation.getMessage().equals(message)));
    }
}
