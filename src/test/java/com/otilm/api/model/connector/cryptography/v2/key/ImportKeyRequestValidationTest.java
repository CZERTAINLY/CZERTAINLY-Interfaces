package com.otilm.api.model.connector.cryptography.v2.key;

import com.otilm.api.model.client.cryptography.key.KeyRequestType;
import com.otilm.api.model.common.enums.cryptography.KeyAlgorithm;
import com.otilm.api.model.connector.common.v2.OperationExecutionMode;
import com.otilm.api.model.connector.cryptography.v2.material.EncryptedKeyMaterialV2Dto;
import com.otilm.api.testsupport.ValidatorFixture;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;

import static com.otilm.api.model.connector.cryptography.v2.utils.CryptographyDtoFixtures.validEncryptedKeyMaterial;
import static com.otilm.api.model.connector.cryptography.v2.utils.CryptographyDtoFixtures.withValidTokenProfileScope;
import static com.otilm.api.model.connector.cryptography.v2.utils.CryptographyDtoFixtures.withValidTokenScope;
import static com.otilm.api.testsupport.ConstraintViolationAssertions.assertHasViolation;
import static com.otilm.api.testsupport.ConstraintViolationAssertions.assertNoViolations;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImportKeyRequestValidationTest {

    @AutoClose
    private static final ValidatorFixture VALIDATORS = new ValidatorFixture();

    private static final Validator VALIDATOR = VALIDATORS.validator();

    private static final String KEY_IMPORT_ID = "b9d41c0e-7a52-4f7e-9dd0-2f8a1c4e9b31";

    private static final String KEY_REFERENCE = "1c9a7e58-33d2-4b0a-8f4e-6a91d27c5f10";

    private static final String TRANSPORT_PASSPHRASE = "8FQmS3ZbW1xkP0vTqA9rYcE4uHnJ6dLiKgOw2sXeVm0";

    @Test
    void importRequest_hasNoViolations_whenFullyPopulated() {
        // given
        ImportKeyRequestV2Dto request = validImportRequest();

        // when
        Set<ConstraintViolation<ImportKeyRequestV2Dto>> violations = VALIDATOR.validate(request);

        // then
        assertNoViolations(violations);
    }

    @Test
    void importRequest_requiresKeyImportId() {
        // given
        ImportKeyRequestV2Dto request = validImportRequest();
        request.setKeyImportId(null);

        // when
        Set<ConstraintViolation<ImportKeyRequestV2Dto>> violations = VALIDATOR.validate(request);

        // then
        assertHasViolation(violations, "keyImportId", "keyImportId is required");
    }

    @Test
    void importRequest_rejectsKeyImportIdLongerThanTheContractBound() {
        // given
        ImportKeyRequestV2Dto request = validImportRequest();
        request.setKeyImportId("i".repeat(257));

        // when
        Set<ConstraintViolation<ImportKeyRequestV2Dto>> violations = VALIDATOR.validate(request);

        // then
        assertHasViolation(violations, "keyImportId", "keyImportId must contain between 1 and 256 characters");
    }

    @Test
    void importRequest_requiresKeyReference() {
        // given
        ImportKeyRequestV2Dto request = validImportRequest();
        request.setKeyReference(null);

        // when
        Set<ConstraintViolation<ImportKeyRequestV2Dto>> violations = VALIDATOR.validate(request);

        // then
        assertHasViolation(violations, "keyReference", "keyReference is required");
    }

    @Test
    void importRequest_rejectsKeyReferenceThatIsNotAUuid() {
        // given
        ImportKeyRequestV2Dto request = validImportRequest();
        request.setKeyReference("not-a-uuid");

        // when
        Set<ConstraintViolation<ImportKeyRequestV2Dto>> violations = VALIDATOR.validate(request);

        // then
        assertHasViolation(violations, "keyReference", "keyReference must be a canonical UUID");
    }

    @Test
    void importRequest_requiresExecutionMode() {
        // given
        ImportKeyRequestV2Dto request = validImportRequest();
        request.setExecutionMode(null);

        // when
        Set<ConstraintViolation<ImportKeyRequestV2Dto>> violations = VALIDATOR.validate(request);

        // then
        assertHasViolation(violations, "executionMode", "executionMode is required");
    }

    @Test
    void importRequest_requiresKeyRequestType() {
        // given
        ImportKeyRequestV2Dto request = validImportRequest();
        request.setKeyRequestType(null);

        // when
        Set<ConstraintViolation<ImportKeyRequestV2Dto>> violations = VALIDATOR.validate(request);

        // then
        assertHasViolation(violations, "keyRequestType", "keyRequestType is required");
    }

    @Test
    void importRequest_requiresImportKeyAttributes() {
        // given
        ImportKeyRequestV2Dto request = validImportRequest();
        request.setImportKeyAttributes(null);

        // when
        Set<ConstraintViolation<ImportKeyRequestV2Dto>> violations = VALIDATOR.validate(request);

        // then
        assertHasViolation(violations, "importKeyAttributes",
                "importKeyAttributes is required (may be empty list, but must be present)");
    }

    @Test
    void importRequest_rejectsNullImportKeyAttributeEntries() {
        // given
        ImportKeyRequestV2Dto request = validImportRequest();
        request.setImportKeyAttributes(Collections.singletonList(null));

        // when
        Set<ConstraintViolation<ImportKeyRequestV2Dto>> violations = VALIDATOR.validate(request);

        // then
        assertHasViolation(violations, "importKeyAttributes[0].<list element>",
                "importKeyAttributes must not contain null entries");
    }

    @Test
    void importRequest_requiresMaterial() {
        // given
        ImportKeyRequestV2Dto request = validImportRequest();
        request.setMaterial(null);

        // when
        Set<ConstraintViolation<ImportKeyRequestV2Dto>> violations = VALIDATOR.validate(request);

        // then
        assertHasViolation(violations, "material", "material is required");
    }

    @Test
    void importRequest_cascadesMaterialValidation() {
        // given
        ImportKeyRequestV2Dto request = validImportRequest();
        request.setMaterial(new EncryptedKeyMaterialV2Dto());

        // when
        Set<ConstraintViolation<ImportKeyRequestV2Dto>> violations = VALIDATOR.validate(request);

        // then
        assertHasViolation(violations, "material.encryptedPrivateKeyInfo", "encryptedPrivateKeyInfo is required");
    }

    @Test
    void importRequest_requiresPassphrase() {
        // given
        ImportKeyRequestV2Dto request = validImportRequest();
        request.setPassphrase(null);

        // when
        Set<ConstraintViolation<ImportKeyRequestV2Dto>> violations = VALIDATOR.validate(request);

        // then
        assertHasViolation(violations, "passphrase", "passphrase is required");
    }

    @Test
    void importRequest_requiresExportableDecision() {
        // given
        ImportKeyRequestV2Dto request = validImportRequest();
        request.setExportable(null);

        // when
        Set<ConstraintViolation<ImportKeyRequestV2Dto>> violations = VALIDATOR.validate(request);

        // then
        assertHasViolation(violations, "exportable", "exportable is required");
    }

    @Test
    void toString_redactsPassphraseAndMaterial() {
        // given
        ImportKeyRequestV2Dto request = validImportRequest();

        // when
        String rendered = request.toString();

        // then
        assertTrue(!rendered.contains(TRANSPORT_PASSPHRASE), "toString must not render the passphrase");
        assertTrue(!rendered.contains("passphrase="), "toString must not name the passphrase field");
    }

    @Test
    void attributesRequest_requiresKeyRequestType() {
        // given
        ImportKeyAttributesRequestV2Dto request = withValidTokenProfileScope(new ImportKeyAttributesRequestV2Dto());

        // when
        Set<ConstraintViolation<ImportKeyAttributesRequestV2Dto>> violations = VALIDATOR.validate(request);

        // then
        assertHasViolation(violations, "keyRequestType", "keyRequestType is required");
    }

    @Test
    void resultRequest_requiresKeyImportId() {
        // given
        ImportKeyResultRequestV2Dto request = withValidTokenScope(new ImportKeyResultRequestV2Dto());

        // when
        Set<ConstraintViolation<ImportKeyResultRequestV2Dto>> violations = VALIDATOR.validate(request);

        // then
        assertHasViolation(violations, "keyImportId", "keyImportId is required");
    }

    @Test
    void resultRequest_hasNoViolations_whenFullyPopulated() {
        // given
        ImportKeyResultRequestV2Dto request = withValidTokenScope(new ImportKeyResultRequestV2Dto());
        request.setKeyImportId(KEY_IMPORT_ID);

        // when
        Set<ConstraintViolation<ImportKeyResultRequestV2Dto>> violations = VALIDATOR.validate(request);

        // then
        assertNoViolations(violations);
    }

    @Test
    void importableKeyType_hasNoViolations_whenFullyPopulated() {
        // given
        ImportableKeyTypeV2Dto importableKeyType = validImportableKeyType();

        // when
        Set<ConstraintViolation<ImportableKeyTypeV2Dto>> violations = VALIDATOR.validate(importableKeyType);

        // then
        assertNoViolations(violations);
    }

    @Test
    void importableKeyType_requiresKeyRequestType() {
        // given
        ImportableKeyTypeV2Dto importableKeyType = validImportableKeyType();
        importableKeyType.setKeyRequestType(null);

        // when
        Set<ConstraintViolation<ImportableKeyTypeV2Dto>> violations = VALIDATOR.validate(importableKeyType);

        // then
        assertHasViolation(violations, "keyRequestType", "keyRequestType is required");
    }

    @Test
    void importableKeyType_requiresAtLeastOneAlgorithm() {
        // given
        ImportableKeyTypeV2Dto importableKeyType = validImportableKeyType();
        importableKeyType.setAlgorithms(Set.of());

        // when
        Set<ConstraintViolation<ImportableKeyTypeV2Dto>> violations = VALIDATOR.validate(importableKeyType);

        // then
        assertHasViolation(violations, "algorithms", "algorithms must contain at least one algorithm");
    }

    @Test
    void importableKeyType_rejectsAnAlgorithmTheConnectorCouldNotName() {
        // given
        ImportableKeyTypeV2Dto importableKeyType = validImportableKeyType();
        importableKeyType.setAlgorithms(Set.of(KeyAlgorithm.RSA, KeyAlgorithm.UNKNOWN));

        // when
        Set<ConstraintViolation<ImportableKeyTypeV2Dto>> violations = VALIDATOR.validate(importableKeyType);

        // then
        assertHasViolation(violations, "namedAlgorithms",
                "algorithms must name the algorithms the connector accepts and must not contain Unknown");
    }

    private static ImportKeyRequestV2Dto validImportRequest() {
        ImportKeyRequestV2Dto request = withValidTokenProfileScope(new ImportKeyRequestV2Dto());
        request.setKeyImportId(KEY_IMPORT_ID);
        request.setKeyReference(KEY_REFERENCE);
        request.setExecutionMode(OperationExecutionMode.SYNCHRONOUS);
        request.setKeyRequestType(KeyRequestType.KEY_PAIR);
        request.setImportKeyAttributes(java.util.List.of());
        request.setMaterial(validEncryptedKeyMaterial());
        request.setPassphrase(TRANSPORT_PASSPHRASE);
        request.setExportable(Boolean.TRUE);
        return request;
    }

    private static ImportableKeyTypeV2Dto validImportableKeyType() {
        ImportableKeyTypeV2Dto importableKeyType = new ImportableKeyTypeV2Dto();
        importableKeyType.setKeyRequestType(KeyRequestType.KEY_PAIR);
        importableKeyType.setAlgorithms(Set.of(KeyAlgorithm.RSA, KeyAlgorithm.ECDSA));
        return importableKeyType;
    }
}
