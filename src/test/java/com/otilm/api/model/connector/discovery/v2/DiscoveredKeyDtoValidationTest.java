package com.otilm.api.model.connector.discovery.v2;

import com.otilm.api.model.common.enums.cryptography.KeyAlgorithm;
import com.otilm.api.model.common.enums.cryptography.KeyFormat;
import com.otilm.api.model.common.enums.cryptography.KeyType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Proves the normative rule stated in {@link DiscoveredKeyDto}'s javadoc — key material never
 * traverses discovery — is actually enforced by bean validation, not just documented.
 */
class DiscoveredKeyDtoValidationTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void setup() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void teardown() {
        factory.close();
    }

    private DiscoveredKeyDto validBase() {
        DiscoveredKeyDto dto = new DiscoveredKeyDto();
        dto.setType(KeyType.PUBLIC_KEY);
        dto.setAlgorithm(KeyAlgorithm.RSA);
        return dto;
    }

    @Test
    void privateKeyInfoFormatIsRejectedRegardlessOfType() {
        DiscoveredKeyDto dto = validBase();
        dto.setPublicKeyFormat(KeyFormat.PRKI);
        dto.setPublicKey("MIIBOgIBAAJBAK...");

        Set<ConstraintViolation<DiscoveredKeyDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "PRKI must never be an accepted publicKeyFormat");
    }

    @Test
    void encryptedPrivateKeyInfoFormatIsRejected() {
        DiscoveredKeyDto dto = validBase();
        dto.setPublicKeyFormat(KeyFormat.EPRKI);
        dto.setPublicKey("MIIBOgIBAAJBAK...");

        Set<ConstraintViolation<DiscoveredKeyDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "EPRKI must never be an accepted publicKeyFormat");
    }

    @Test
    void privateKeyTypeCarryingPublicKeyBytesIsRejected() {
        // This is the exact shape the finding calls out: a Private-type item that still carries
        // a PrivateKeyInfo publicKeyFormat and public key bytes.
        DiscoveredKeyDto dto = new DiscoveredKeyDto();
        dto.setType(KeyType.PRIVATE_KEY);
        dto.setAlgorithm(KeyAlgorithm.RSA);
        dto.setPublicKeyFormat(KeyFormat.PRKI);
        dto.setPublicKey("MIIBOgIBAAJBAK...");

        Set<ConstraintViolation<DiscoveredKeyDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "a PRIVATE_KEY item must never carry key bytes");
    }

    @Test
    void secretKeyTypeCarryingPublicKeyFieldIsRejected() {
        DiscoveredKeyDto dto = new DiscoveredKeyDto();
        dto.setType(KeyType.SECRET_KEY);
        dto.setAlgorithm(KeyAlgorithm.RSA);
        // A secret key has no "public" part at all, so even a well-formed SPKI value here is forbidden.
        dto.setPublicKeyFormat(KeyFormat.SPKI);
        dto.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A");

        Set<ConstraintViolation<DiscoveredKeyDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "a SECRET_KEY item must never carry publicKey/publicKeyFormat");
    }

    @Test
    void splitKeyTypeCarryingPublicKeyIsRejected() {
        DiscoveredKeyDto dto = new DiscoveredKeyDto();
        dto.setType(KeyType.SPLIT_KEY);
        dto.setAlgorithm(KeyAlgorithm.RSA);
        dto.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A");
        // publicKeyFormat left unset: publicKey alone must still be enough to trip the rule.

        Set<ConstraintViolation<DiscoveredKeyDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "a SPLIT_KEY item must never carry publicKey material");
    }

    @Test
    void typeWithoutPublicPartCarryingOnlyPublicKeyIsRejectedOnPublicKeyPathOnly() {
        DiscoveredKeyDto dto = new DiscoveredKeyDto();
        dto.setType(KeyType.PRIVATE_KEY);
        dto.setAlgorithm(KeyAlgorithm.RSA);
        dto.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A");
        // publicKeyFormat intentionally left unset.

        Set<ConstraintViolation<DiscoveredKeyDto>> violations = validator.validate(dto);
        assertEquals(Set.of("publicKey"), propertyPaths(violations),
                "only publicKey is populated, so only publicKey should be named");
    }

    @Test
    void typeWithoutPublicPartCarryingOnlyPublicKeyFormatIsRejectedOnPublicKeyFormatPathOnly() {
        DiscoveredKeyDto dto = new DiscoveredKeyDto();
        dto.setType(KeyType.PRIVATE_KEY);
        dto.setAlgorithm(KeyAlgorithm.RSA);
        // A non-private format on its own, so only the "no public part for this type" rule trips
        // (not the separate PRKI/EPRKI-format rule) — isolates the property-path behavior under test.
        dto.setPublicKeyFormat(KeyFormat.SPKI);
        // publicKey intentionally left unset.

        Set<ConstraintViolation<DiscoveredKeyDto>> violations = validator.validate(dto);
        assertEquals(Set.of("publicKeyFormat"), propertyPaths(violations),
                "only publicKeyFormat is populated, so only publicKeyFormat should be named — "
                        + "not publicKey, which was never set");
    }

    @Test
    void typeWithoutPublicPartCarryingBothFieldsIsRejectedOnBothPaths() {
        DiscoveredKeyDto dto = new DiscoveredKeyDto();
        dto.setType(KeyType.PRIVATE_KEY);
        dto.setAlgorithm(KeyAlgorithm.RSA);
        dto.setPublicKeyFormat(KeyFormat.SPKI);
        dto.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A");

        Set<ConstraintViolation<DiscoveredKeyDto>> violations = validator.validate(dto);
        assertEquals(Set.of("publicKey", "publicKeyFormat"), propertyPaths(violations),
                "both fields are populated, so both should be named");
    }

    private static Set<String> propertyPaths(Set<ConstraintViolation<DiscoveredKeyDto>> violations) {
        return violations.stream()
                .map(v -> v.getPropertyPath().toString())
                .collect(Collectors.toSet());
    }

    @Test
    void privateKeyExistenceOnlyReportIsValid() {
        DiscoveredKeyDto dto = new DiscoveredKeyDto();
        dto.setType(KeyType.PRIVATE_KEY);
        dto.setAlgorithm(KeyAlgorithm.RSA);
        dto.setFingerprint("9c1a2b3d4e5f60718293a4b5c6d7e8f90123456789abcdef0123456789abcde");
        // publicKey and publicKeyFormat intentionally left unset.

        Set<ConstraintViolation<DiscoveredKeyDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "an existence-only private-key report must pass validation cleanly");
    }

    @Test
    void legitimatePublicKeyReportIsValid() {
        DiscoveredKeyDto dto = validBase();
        dto.setPublicKeyFormat(KeyFormat.SPKI);
        dto.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A");

        Set<ConstraintViolation<DiscoveredKeyDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "a PUBLIC_KEY report with SPKI-formatted public key bytes must be valid");
    }
}
