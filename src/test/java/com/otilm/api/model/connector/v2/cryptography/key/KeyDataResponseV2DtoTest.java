package com.otilm.api.model.connector.v2.cryptography.key;

import com.otilm.api.model.connector.cryptography.v2.key.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.otilm.api.model.connector.v2.cryptography.ValidationTestUtils.assertViolation;
import static com.otilm.api.model.connector.v2.cryptography.ValidationTestUtils.validate;

class KeyDataResponseV2DtoTest {

    @Test
    void publicKeyResponse_requiresMetadataAndDescriptor() {
        // given
        var response = new PublicKeyDataResponseV2Dto();

        // when
        var missingFields = validate(response);
        response.setKeyMeta(Collections.singletonList(null));
        var nullMetadata = validate(response);

        // then
        assertViolation(missingFields, "keyMeta", NotEmpty.class);
        assertViolation(missingFields, "keyData", NotNull.class);
        assertViolation(nullMetadata, "keyMeta[0].<list element>", NotNull.class);
    }

    @Test
    void privateKeyResponse_requiresMetadataAndDescriptor() {
        // given
        var response = new PrivateKeyDataResponseV2Dto();

        // when
        var missingFields = validate(response);
        response.setKeyMeta(Collections.singletonList(null));
        var nullMetadata = validate(response);

        // then
        assertViolation(missingFields, "keyMeta", NotEmpty.class);
        assertViolation(missingFields, "keyData", NotNull.class);
        assertViolation(nullMetadata, "keyMeta[0].<list element>", NotNull.class);
    }

    @Test
    void responseEnvelopes_cascadeDescriptorValidation() {
        // given
        var secretEnvelope = new SecretKeyDataResponseV2Dto();
        secretEnvelope.setKeyData(new SecretKeyDataV2Dto());
        var genericEnvelope = new KeyDataResponseV2Dto();
        genericEnvelope.setKeyData(new SecretKeyDataV2Dto());

        // when
        var secretViolations = validate(secretEnvelope);
        var genericViolations = validate(genericEnvelope);

        // then
        assertViolation(secretViolations, "keyData.algorithm", NotNull.class);
        assertViolation(genericViolations, "keyData.algorithm", NotNull.class);
    }

    @Test
    void keyPairEnvelope_cascadesBothDescriptors() {
        // given
        var publicResponse = new PublicKeyDataResponseV2Dto();
        publicResponse.setKeyMeta(List.of());
        var privateResponse = new PrivateKeyDataResponseV2Dto();
        privateResponse.setKeyMeta(List.of());
        var response = new KeyPairDataResponseV2Dto();
        response.setPublicKeyData(publicResponse);
        response.setPrivateKeyData(privateResponse);

        // when
        var violations = validate(response);

        // then
        assertViolation(violations, "publicKeyData.keyData", NotNull.class);
        assertViolation(violations, "privateKeyData.keyData", NotNull.class);
    }
}
