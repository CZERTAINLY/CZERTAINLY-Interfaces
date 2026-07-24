package com.otilm.api.model.connector.v2.cryptography.key;

import com.otilm.api.model.common.enums.cryptography.KeyAlgorithm;
import com.otilm.api.model.connector.cryptography.v2.key.KeyRoleV2;
import com.otilm.api.model.connector.cryptography.v2.key.PublicKeyDataV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.SecretKeyDataV2Dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.security.KeyPairGenerator;
import java.util.List;

import static com.otilm.api.model.connector.v2.cryptography.MetadataTestUtils.stringMetadata;
import static com.otilm.api.model.connector.v2.cryptography.ValidationTestUtils.assertViolation;
import static com.otilm.api.model.connector.v2.cryptography.ValidationTestUtils.validate;
import static org.junit.jupiter.api.Assertions.*;

class KeyDataV2DtoTest {

    @Test
    void beanValidation_requiresAlgorithmAndPositiveLength() {
        // given
        var keyData = new SecretKeyDataV2Dto();
        keyData.setLength(0);

        // when
        var violations = validate(keyData);

        // then
        assertViolation(violations, "algorithm", NotNull.class);
        assertViolation(violations, "length", Positive.class);
    }

    @Test
    void validate_rejectsInvalidMetadataEntry() {
        // given
        var keyData = validSecretKeyData();
        keyData.setMetadata(List.of(stringMetadata("", "value")));

        // when
        Executable validate = keyData::validate;

        // then
        assertThrows(IllegalArgumentException.class, validate);
    }

    @Test
    void setType_rejectsRoleThatDoesNotMatchConcreteSubtype() {
        // given
        var keyData = validSecretKeyData();

        // when
        Executable setMismatchedRole = () -> keyData.setType(KeyRoleV2.PUBLIC);

        // then
        assertThrows(IllegalArgumentException.class, setMismatchedRole);
    }

    @Test
    void publicKeySpki_rejectsMalformedEncoding() {
        // given
        var keyData = validPublicKeyData();
        var malformedSpki = new byte[]{1, 2, 3};

        // when
        Executable setMalformedSpki = () -> keyData.setPublicKeySpki(malformedSpki);

        // then
        assertThrows(IllegalArgumentException.class, setMalformedSpki);
    }

    @Test
    void validate_rejectsSpkiThatDoesNotMatchDeclaredAlgorithm() throws Exception {
        // given
        var rsaKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
        rsaKeyPairGenerator.initialize(2048);
        var rsaSpki = rsaKeyPairGenerator.generateKeyPair().getPublic().getEncoded();
        var keyData = validPublicKeyData();
        keyData.setAlgorithm(KeyAlgorithm.ECDSA);
        keyData.setPublicKeySpki(rsaSpki);

        // when
        Executable validate = keyData::validate;

        // then
        assertThrows(IllegalArgumentException.class, validate);
    }

    @Test
    void publicKeySpki_isDefensivelyCopied() throws Exception {
        // given
        var rsaKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
        rsaKeyPairGenerator.initialize(2048);
        var rsaSpki = rsaKeyPairGenerator.generateKeyPair().getPublic().getEncoded();
        var keyData = validPublicKeyData();

        // when
        keyData.setPublicKeySpki(rsaSpki);
        var returnedSpki = keyData.getPublicKeySpki();
        returnedSpki[0] ^= 1;

        // then
        assertArrayEquals(rsaSpki, keyData.getPublicKeySpki());
        assertDoesNotThrow(keyData::validate);
    }

    private static SecretKeyDataV2Dto validSecretKeyData() {
        var keyData = new SecretKeyDataV2Dto();
        keyData.setAlgorithm(KeyAlgorithm.AES);
        keyData.setLength(256);
        return keyData;
    }

    private static PublicKeyDataV2Dto validPublicKeyData() {
        var keyData = new PublicKeyDataV2Dto();
        keyData.setAlgorithm(KeyAlgorithm.RSA);
        keyData.setLength(2048);
        return keyData;
    }
}
