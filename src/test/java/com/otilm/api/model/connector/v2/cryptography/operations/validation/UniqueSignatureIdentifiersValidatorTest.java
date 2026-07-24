package com.otilm.api.model.connector.v2.cryptography.operations.validation;

import com.otilm.api.model.connector.cryptography.v2.operations.data.SignatureRequestDataV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.validation.UniqueSignatureIdentifiersValidator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UniqueSignatureIdentifiersValidatorTest {

    private final UniqueSignatureIdentifiersValidator validator = new UniqueSignatureIdentifiersValidator();

    @Test
    void isValid_returnsFalse_forSyntheticBatchWithDuplicateIdentifiers() {
        // given
        var duplicateIdentifier = "duplicate";
        var items = List.of(item(duplicateIdentifier), item(duplicateIdentifier));

        // when
        var valid = validator.isValid(items, null);

        // then
        assertFalse(valid);
    }

    @Test
    void isValid_returnsTrue_forSyntheticBatchWithDistinctCaseSensitiveIdentifiers() {
        // given
        var lowerCaseIdentifier = "item";
        var upperCaseIdentifier = "ITEM";
        var items = List.of(item(lowerCaseIdentifier), item(upperCaseIdentifier));

        // when
        var valid = validator.isValid(items, null);

        // then
        assertTrue(valid);
    }

    @Test
    void isValid_ignoresItemsValidatedByOtherConstraints() {
        // given
        List<SignatureRequestDataV2Dto> items = Arrays.asList(null, item(null), item(" "));

        // when
        var valid = validator.isValid(items, null);

        // then
        assertTrue(valid);
    }

    @Test
    void isValid_returnsTrue_forNullList() {
        // given
        List<SignatureRequestDataV2Dto> items = null;

        // when
        var valid = validator.isValid(items, null);

        // then
        assertTrue(valid);
    }

    private static SignatureRequestDataV2Dto item(String identifier) {
        return new SignatureRequestDataV2Dto(new byte[]{1}, identifier);
    }
}
