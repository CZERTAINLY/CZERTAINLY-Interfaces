package com.otilm.api.model.connector.v2.cryptography;

import com.otilm.api.model.common.attribute.v2.MetadataAttributeV2;
import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationCancelRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationStatusRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.SignDataRequestV2Dto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.otilm.api.model.connector.v2.cryptography.MetadataTestUtils.stringMetadata;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MetadataHandleValidationTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void existingKeyHandleMustBeNonEmptyAndContainValidNonNullEntries() {
        SignDataRequestV2Dto request = new SignDataRequestV2Dto();

        assertInvalidAt(request, "keyMeta");
        request.setKeyMeta(List.of());
        assertInvalidAt(request, "keyMeta");
        request.setKeyMeta(Collections.singletonList(null));
        assertInvalidAt(request, "keyMeta[0].<list element>");
        request.setKeyMeta(List.of(stringMetadata("", "key-123")));
        assertInvalidAt(request, "keyMeta[0].name");
        request.setKeyMeta(List.of(stringMetadata("keyId", "key-123")));
        assertFalse(VALIDATOR.validateProperty(request, "keyMeta").stream().findAny().isPresent());
    }

    @Test
    void keyOperationStatusAndCancellationRequireTrackingHandles() {
        KeyOperationStatusRequestV2Dto status = new KeyOperationStatusRequestV2Dto();
        KeyOperationCancelRequestV2Dto cancel = new KeyOperationCancelRequestV2Dto();

        assertInvalidAt(status, "operationMeta");
        assertInvalidAt(cancel, "operationMeta");
        status.setOperationMeta(List.of());
        cancel.setOperationMeta(Collections.singletonList(null));
        assertInvalidAt(status, "operationMeta");
        assertInvalidAt(cancel, "operationMeta[0].<list element>");

        List<MetadataAttributeV2> handle = List.of(stringMetadata("operationId", "operation-123"));
        status.setOperationMeta(handle);
        cancel.setOperationMeta(handle);
        assertTrue(VALIDATOR.validate(status).stream().noneMatch(v -> v.getPropertyPath().toString().startsWith("operationMeta")));
        assertTrue(VALIDATOR.validate(cancel).stream().noneMatch(v -> v.getPropertyPath().toString().startsWith("operationMeta")));
    }

    private static void assertInvalidAt(Object value, String path) {
        assertTrue(VALIDATOR.validate(value).stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals(path)));
    }
}
