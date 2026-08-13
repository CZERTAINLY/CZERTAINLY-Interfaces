package com.otilm.api.model.connector.cryptography.v2.operations.validation;

import com.otilm.api.model.connector.cryptography.v2.operations.data.IdentifiedDataV2Dto;
import com.otilm.api.testsupport.ValidatorFixture;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UniqueIdentifiersValidatorTest {

    @AutoClose
    private static final ValidatorFixture VALIDATORS = new ValidatorFixture();
    private static final Validator VALIDATOR = VALIDATORS.validator();

    @Test
    void validate_reportsDuplicateItemIndex_forEachRepeatedIdentifier() {
        // given
        String firstIdentifier = "first";
        String secondIdentifier = "second";
        IdentifiedDataBatch batch = new IdentifiedDataBatch(
                List.of(item(firstIdentifier), item(secondIdentifier), item(firstIdentifier), item(firstIdentifier)));

        // when
        Set<ConstraintViolation<IdentifiedDataBatch>> violations = VALIDATOR.validate(batch);

        // then
        assertEquals(Set
                .of("items[2].identifier: identifiers must be unique within the batch; duplicates item at index 0",
                        "items[3].identifier: identifiers must be unique within the batch; duplicates item at index 0"),
                violationDescriptions(violations));
    }

    @Test
    void validate_ignoresValuesHandledByOtherConstraints() {
        // given
        IdentifiedDataBatch batch = new IdentifiedDataBatch(Arrays.asList(null, item(null), item(" ")));

        // when
        Set<ConstraintViolation<IdentifiedDataBatch>> violations = VALIDATOR.validate(batch);

        // then
        assertTrue(violations.isEmpty());
    }

    @Test
    void validate_acceptsNullList() {
        // given
        IdentifiedDataBatch batch = new IdentifiedDataBatch(null);

        // when
        Set<ConstraintViolation<IdentifiedDataBatch>> violations = VALIDATOR.validate(batch);

        // then
        assertTrue(violations.isEmpty());
    }

    private static Set<String> violationDescriptions(Set<ConstraintViolation<IdentifiedDataBatch>> violations) {
        return violations
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(java.util.stream.Collectors.toSet());
    }

    private static TestItem item(String identifier) {
        return new TestItem(identifier);
    }

    private record IdentifiedDataBatch(@UniqueIdentifiers List<TestItem> items) {
    }

    private record TestItem(String identifier) implements IdentifiedDataV2Dto {

        @Override
        public String getIdentifier() {
            return identifier;
        }
    }
}
