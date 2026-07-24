package com.otilm.api.model.connector.v2.cryptography;

import com.otilm.api.model.connector.cryptography.v2.KeyScopedRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.TokenProfileScopedRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.token.TokenScopedRequestV2Dto;
import com.otilm.api.model.core.cryptography.key.KeyUsage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.otilm.api.model.connector.v2.cryptography.MetadataTestUtils.stringMetadata;
import static com.otilm.api.model.connector.v2.cryptography.ValidationTestUtils.*;

class ScopedRequestV2DtoTest {

    @Test
    void tokenScope_rejectsMissingTokenAttributes_andAcceptsEmptyList() {
        // given
        var request = new TokenScopedRequestV2Dto();

        // when
        var violations = validate(request);

        // then
        assertViolation(violations, "tokenAttributes", NotNull.class);
        request.setTokenAttributes(List.of());
        assertNoViolations(request);
    }

    @Test
    void tokenProfileScope_validatesProfileAttributesAndKeyUsages() {
        // given
        var request = validTokenProfileScope();

        // when
        request.setTokenProfileAttributes(null);
        var missingAttributes = validate(request);
        request.setTokenProfileAttributes(List.of());
        request.setKeyUsages(Set.of());
        var emptyUsages = validate(request);
        request.setKeyUsages(Collections.singleton(null));
        var nullUsage = validate(request);

        // then
        assertViolation(missingAttributes, "tokenProfileAttributes", NotNull.class);
        assertViolation(emptyUsages, "keyUsages", NotEmpty.class);
        assertViolation(nullUsage, "keyUsages[].<iterable element>", NotNull.class);
    }

    @Test
    void keyScope_validatesNonEmptyNonNullNestedMetadata() {
        // given
        var request = validKeyScope();

        // when
        request.setKeyMeta(List.of());
        var emptyMetadata = validate(request);
        request.setKeyMeta(Collections.singletonList(null));
        var nullMetadata = validate(request);
        request.setKeyMeta(List.of(stringMetadata("", "key-1")));
        var invalidMetadata = validate(request);

        // then
        assertViolation(emptyMetadata, "keyMeta", NotEmpty.class);
        assertViolation(nullMetadata, "keyMeta[0].<list element>", NotNull.class);
        assertViolation(invalidMetadata, "keyMeta[0].name", NotBlank.class);
    }

    private static TokenProfileScopedRequestV2Dto validTokenProfileScope() {
        var request = new TokenProfileScopedRequestV2Dto();
        request.setTokenAttributes(List.of());
        request.setTokenProfileAttributes(List.of());
        request.setKeyUsages(Set.of(KeyUsage.SIGN));
        return request;
    }

    private static KeyScopedRequestV2Dto validKeyScope() {
        var request = new KeyScopedRequestV2Dto();
        request.setTokenAttributes(List.of());
        request.setTokenProfileAttributes(List.of());
        request.setKeyUsages(Set.of(KeyUsage.SIGN));
        request.setKeyMeta(List.of(stringMetadata("keyId", "key-1")));
        return request;
    }
}
