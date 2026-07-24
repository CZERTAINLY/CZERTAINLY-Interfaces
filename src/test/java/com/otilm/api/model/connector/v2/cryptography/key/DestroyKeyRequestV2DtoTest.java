package com.otilm.api.model.connector.v2.cryptography.key;

import com.otilm.api.model.connector.common.v2.OperationExecutionMode;
import com.otilm.api.model.connector.cryptography.v2.key.DestroyKeyRequestV2Dto;
import com.otilm.api.model.core.cryptography.key.KeyUsage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static com.otilm.api.model.connector.v2.cryptography.MetadataTestUtils.stringMetadata;
import static com.otilm.api.model.connector.v2.cryptography.ValidationTestUtils.assertViolation;
import static com.otilm.api.model.connector.v2.cryptography.ValidationTestUtils.validate;

class DestroyKeyRequestV2DtoTest {

    @Test
    void validation_rejectsMissingExecutionMode() {
        // given
        var request = validRequest();
        request.setExecutionMode(null);

        // when
        var violations = validate(request);

        // then
        assertViolation(violations, "executionMode", NotNull.class);
    }

    @Test
    void validation_enforcesInheritedKeyScope() {
        // given
        var request = validRequest();
        request.setKeyMeta(null);

        // when
        var violations = validate(request);

        // then
        assertViolation(violations, "keyMeta", NotEmpty.class);
    }

    private static DestroyKeyRequestV2Dto validRequest() {
        var request = new DestroyKeyRequestV2Dto();
        request.setTokenAttributes(List.of());
        request.setTokenProfileAttributes(List.of());
        request.setKeyUsages(Set.of(KeyUsage.SIGN));
        request.setKeyMeta(List.of(stringMetadata("keyId", "key-1")));
        request.setExecutionMode(OperationExecutionMode.SYNCHRONOUS);
        return request;
    }
}
