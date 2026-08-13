package com.otilm.api.model.client.signing.profile.validation;

import com.otilm.api.model.client.attribute.RequestAttributeV3;
import com.otilm.api.model.client.signing.profile.SigningProfileRequestDto;
import com.otilm.api.model.client.signing.profile.scheme.DelegatedSigningRequestDto;
import com.otilm.api.model.client.signing.profile.scheme.StaticKeyManagedSigningRequestDto;
import com.otilm.api.model.client.signing.profile.workflow.ContentSigningWorkflowRequestDto;
import com.otilm.api.model.client.signing.profile.workflow.RawSigningWorkflowRequestDto;
import com.otilm.api.model.client.signing.profile.workflow.TimestampingWorkflowRequestDto;
import com.otilm.api.model.client.signing.profile.workflow.timestamp.InternalTimestampSourceRequestDto;
import com.otilm.api.model.common.signature.SignatureFamily;
import com.otilm.api.model.common.signature.SignatureLevel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SigningProfileConstraintValidatorsTest {

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

    private <T> boolean hasViolationOn(Set<ConstraintViolation<T>> violations, String path) {
        return violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals(path));
    }

    private StaticKeyManagedSigningRequestDto managedScheme() {
        StaticKeyManagedSigningRequestDto scheme = new StaticKeyManagedSigningRequestDto();
        scheme.setCertificateUuid(UUID.randomUUID());
        return scheme;
    }

    private DelegatedSigningRequestDto delegatedScheme() {
        DelegatedSigningRequestDto scheme = new DelegatedSigningRequestDto();
        scheme.setConnectorUuid(UUID.randomUUID());
        return scheme;
    }

    private ContentSigningWorkflowRequestDto contentSigningWorkflow(SignatureLevel maxLevel) {
        ContentSigningWorkflowRequestDto workflow = new ContentSigningWorkflowRequestDto();
        workflow.setSignatureFormattingConnectorUuid(UUID.randomUUID());
        workflow.setFamily(SignatureFamily.PADES);
        workflow.setMaxLevel(maxLevel);
        return workflow;
    }

    private SigningProfileRequestDto profileRequest(Object scheme, Object workflow) {
        SigningProfileRequestDto dto = new SigningProfileRequestDto();
        dto.setName("test-profile");
        dto.setSigningScheme((com.otilm.api.model.client.signing.profile.scheme.SigningSchemeRequestDto) scheme);
        dto.setWorkflow((com.otilm.api.model.client.signing.profile.workflow.WorkflowRequestDto) workflow);
        return dto;
    }

    // --- ManagedSignatureFormattingConnectorValidator ---

    @Test
    void managedTimestampingNullFormattingUuid_producesViolation() {
        TimestampingWorkflowRequestDto workflow = new TimestampingWorkflowRequestDto();
        assertTrue(hasViolationOn(validator.validate(profileRequest(managedScheme(), workflow)),
                "workflow.signatureFormattingConnectorUuid"));
    }

    @Test
    void managedTimestampingWithFormattingUuid_noFormattingViolation() {
        TimestampingWorkflowRequestDto workflow = new TimestampingWorkflowRequestDto();
        workflow.setSignatureFormattingConnectorUuid(UUID.randomUUID());
        assertFalse(hasViolationOn(validator.validate(profileRequest(managedScheme(), workflow)),
                "workflow.signatureFormattingConnectorUuid"));
    }

    @Test
    void delegatedTimestampingNullFormattingUuid_noFormattingViolation() {
        assertFalse(hasViolationOn(
                validator.validate(profileRequest(delegatedScheme(), new TimestampingWorkflowRequestDto())),
                "workflow.signatureFormattingConnectorUuid"));
    }

    @Test
    void managedContentSigningNullFormattingUuid_producesViolation() {
        assertTrue(hasViolationOn(
                validator.validate(profileRequest(managedScheme(), new ContentSigningWorkflowRequestDto())),
                "workflow.signatureFormattingConnectorUuid"));
    }

    @Test
    void managedContentSigningWithFormattingUuid_noFormattingViolation() {
        ContentSigningWorkflowRequestDto workflow = new ContentSigningWorkflowRequestDto();
        workflow.setSignatureFormattingConnectorUuid(UUID.randomUUID());
        assertFalse(hasViolationOn(validator.validate(profileRequest(managedScheme(), workflow)),
                "workflow.signatureFormattingConnectorUuid"));
    }

    @Test
    void managedRawSigningNullFormattingUuid_noFormattingViolation() {
        assertFalse(
                hasViolationOn(validator.validate(profileRequest(managedScheme(), new RawSigningWorkflowRequestDto())),
                        "workflow.signatureFormattingConnectorUuid"));
    }

    @Test
    void managedTimestampingQualifiedTimestampTrueNullTqcUuid_producesViolation() {
        TimestampingWorkflowRequestDto workflow = new TimestampingWorkflowRequestDto();
        workflow.setQualifiedTimestamp(true);
        assertTrue(hasViolationOn(validator.validate(profileRequest(managedScheme(), workflow)),
                "workflow.timeQualityConfigurationUuid"));
    }

    @Test
    void managedTimestampingQualifiedTimestampTrueWithTqcUuid_noQualificationViolation() {
        TimestampingWorkflowRequestDto workflow = new TimestampingWorkflowRequestDto();
        workflow.setSignatureFormattingConnectorUuid(UUID.randomUUID());
        workflow.setQualifiedTimestamp(true);
        workflow.setTimeQualityConfigurationUuid(UUID.randomUUID());
        assertFalse(hasViolationOn(validator.validate(profileRequest(managedScheme(), workflow)),
                "workflow.timeQualityConfigurationUuid"));
    }

    @Test
    void managedTimestampingQualifiedTimestampFalseNullTqcUuid_noQualificationViolation() {
        TimestampingWorkflowRequestDto workflow = new TimestampingWorkflowRequestDto();
        workflow.setQualifiedTimestamp(false);
        assertFalse(hasViolationOn(validator.validate(profileRequest(managedScheme(), workflow)),
                "workflow.timeQualityConfigurationUuid"));
    }

    @Test
    void managedTimestampingQualifiedTimestampNullNullTqcUuid_noQualificationViolation() {
        assertFalse(hasViolationOn(
                validator.validate(profileRequest(managedScheme(), new TimestampingWorkflowRequestDto())),
                "workflow.timeQualityConfigurationUuid"));
    }

    @Test
    void delegatedTimestampingQualifiedTimestampTrueNullTqcUuid_noQualificationViolation() {
        TimestampingWorkflowRequestDto workflow = new TimestampingWorkflowRequestDto();
        workflow.setQualifiedTimestamp(true);
        assertFalse(hasViolationOn(validator.validate(profileRequest(delegatedScheme(), workflow)),
                "workflow.timeQualityConfigurationUuid"));
    }

    // --- OidValidator (via TimestampingWorkflowRequestDto.defaultPolicyId) ---

    @Test
    void validDefaultPolicyId_noOidViolation() {
        TimestampingWorkflowRequestDto dto = new TimestampingWorkflowRequestDto();
        dto.setDefaultPolicyId("1.2.840.113549.1.9.14");
        assertFalse(hasViolationOn(validator.validate(dto), "defaultPolicyId"));
    }

    @Test
    void invalidDefaultPolicyId_producesOidViolation() {
        TimestampingWorkflowRequestDto dto = new TimestampingWorkflowRequestDto();
        dto.setDefaultPolicyId("not-an-oid");
        assertTrue(hasViolationOn(validator.validate(dto), "defaultPolicyId"));
    }

    @Test
    void nullDefaultPolicyId_noOidViolation() {
        assertFalse(hasViolationOn(validator.validate(new TimestampingWorkflowRequestDto()), "defaultPolicyId"));
    }

    // ASN.1 arc-constraint tests: first arc 0 or 1 → second arc must be 0..39

    @Test
    void oidFirstArc1SecondArc39_noOidViolation() {
        TimestampingWorkflowRequestDto dto = new TimestampingWorkflowRequestDto();
        dto.setDefaultPolicyId("1.39.1");
        assertFalse(hasViolationOn(validator.validate(dto), "defaultPolicyId"));
    }

    @Test
    void oidFirstArc0SecondArc0_noOidViolation() {
        TimestampingWorkflowRequestDto dto = new TimestampingWorkflowRequestDto();
        dto.setDefaultPolicyId("0.0.1");
        assertFalse(hasViolationOn(validator.validate(dto), "defaultPolicyId"));
    }

    @Test
    void oidFirstArc2SecondArcLarge_noOidViolation() {
        TimestampingWorkflowRequestDto dto = new TimestampingWorkflowRequestDto();
        dto.setDefaultPolicyId("2.999.1");
        assertFalse(hasViolationOn(validator.validate(dto), "defaultPolicyId"));
    }

    @Test
    void oidFirstArc1SecondArc40_producesOidViolation() {
        TimestampingWorkflowRequestDto dto = new TimestampingWorkflowRequestDto();
        dto.setDefaultPolicyId("1.40.1");
        assertTrue(hasViolationOn(validator.validate(dto), "defaultPolicyId"));
    }

    @Test
    void oidFirstArc1SecondArc80_producesOidViolation() {
        TimestampingWorkflowRequestDto dto = new TimestampingWorkflowRequestDto();
        dto.setDefaultPolicyId("1.80.1");
        assertTrue(hasViolationOn(validator.validate(dto), "defaultPolicyId"));
    }

    @Test
    void oidFirstArc0SecondArc40_producesOidViolation() {
        TimestampingWorkflowRequestDto dto = new TimestampingWorkflowRequestDto();
        dto.setDefaultPolicyId("0.40.1");
        assertTrue(hasViolationOn(validator.validate(dto), "defaultPolicyId"));
    }

    // --- OidListValidator (via TimestampingWorkflowRequestDto.allowedPolicyIds) ---

    @Test
    void validOidList_noViolation() {
        TimestampingWorkflowRequestDto dto = new TimestampingWorkflowRequestDto();
        dto.setAllowedPolicyIds(java.util.List.of("1.2.3.4.5", "2.16.840.1.101.3.4.2.1"));
        assertFalse(hasViolationOn(validator.validate(dto), "allowedPolicyIds"));
    }

    @Test
    void oidListWithInvalidElement_producesViolation() {
        TimestampingWorkflowRequestDto dto = new TimestampingWorkflowRequestDto();
        dto.setAllowedPolicyIds(java.util.List.of("1.2.3.4.5", "not-an-oid"));
        assertTrue(hasViolationOn(validator.validate(dto), "allowedPolicyIds"));
    }

    @Test
    void nullOidList_noViolation() {
        TimestampingWorkflowRequestDto dto = new TimestampingWorkflowRequestDto();
        dto.setAllowedPolicyIds(null);
        assertFalse(hasViolationOn(validator.validate(dto), "allowedPolicyIds"));
    }

    // --- DefaultPolicyIdValidator ---

    @Test
    void defaultPolicyNotInAllowedPolicies_producesViolation() {
        TimestampingWorkflowRequestDto dto = new TimestampingWorkflowRequestDto();
        dto.setDefaultPolicyId("1.2.3.4.7");
        dto.setAllowedPolicyIds(java.util.List.of("1.2.3.4.5", "1.2.3.4.6"));
        assertTrue(hasViolationOn(validator.validate(dto), "defaultPolicyId"));
    }

    @Test
    void defaultPolicyInAllowedPolicies_noViolation() {
        TimestampingWorkflowRequestDto dto = new TimestampingWorkflowRequestDto();
        dto.setDefaultPolicyId("1.2.3.4.5");
        dto.setAllowedPolicyIds(java.util.List.of("1.2.3.4.5", "1.2.3.4.6"));
        assertFalse(hasViolationOn(validator.validate(dto), "defaultPolicyId"));
    }

    @Test
    void defaultPolicyWithEmptyAllowedPolicies_noViolation() {
        TimestampingWorkflowRequestDto dto = new TimestampingWorkflowRequestDto();
        dto.setDefaultPolicyId("1.2.3.4.5");
        dto.setAllowedPolicyIds(java.util.List.of());
        assertFalse(hasViolationOn(validator.validate(dto), "defaultPolicyId"));
    }

    @Test
    void nullDefaultPolicyWithAllowedPolicies_noViolation() {
        TimestampingWorkflowRequestDto dto = new TimestampingWorkflowRequestDto();
        dto.setAllowedPolicyIds(java.util.List.of("1.2.3.4.5"));
        assertFalse(hasViolationOn(validator.validate(dto), "defaultPolicyId"));
    }

    @Test
    void defaultPolicyNotInAllowedPolicies_violationSurfacesThroughProfileRequest() {
        TimestampingWorkflowRequestDto workflow = new TimestampingWorkflowRequestDto();
        workflow.setSignatureFormattingConnectorUuid(UUID.randomUUID());
        workflow.setDefaultPolicyId("1.2.3.4.7");
        workflow.setAllowedPolicyIds(java.util.List.of("1.2.3.4.5"));
        assertTrue(hasViolationOn(validator.validate(profileRequest(managedScheme(), workflow)),
                "workflow.defaultPolicyId"));
    }

    // --- ContentSigningWorkflowValidator ---

    /** Levels above SIGNED need timestamps, and a profile that cannot get them would fail at signing time instead. */
    @Test
    void aTimestampedLevelWithoutASourceIsRejected() {
        for (SignatureLevel level : List
                .of(SignatureLevel.TIMESTAMPED, SignatureLevel.LONG_TERM, SignatureLevel.ARCHIVAL)) {
            Set<ConstraintViolation<SigningProfileRequestDto>> violations = validator
                    .validate(profileRequest(managedScheme(), contentSigningWorkflow(level)));

            assertTrue(hasViolationOn(violations, "workflow.timestampSource"), level.name() + " was accepted");
        }
    }

    @Test
    void levelBNeedsNoTimestampSource() {
        Set<ConstraintViolation<SigningProfileRequestDto>> violations = validator
                .validate(profileRequest(managedScheme(), contentSigningWorkflow(SignatureLevel.SIGNED)));

        assertFalse(hasViolationOn(violations, "workflow.timestampSource"));
    }

    @Test
    void aTimestampedLevelWithASourceIsAccepted() {
        ContentSigningWorkflowRequestDto workflow = contentSigningWorkflow(SignatureLevel.ARCHIVAL);
        workflow.setTimestampSource(new InternalTimestampSourceRequestDto(UUID.randomUUID()));

        Set<ConstraintViolation<SigningProfileRequestDto>> violations = validator
                .validate(profileRequest(managedScheme(), workflow));

        assertFalse(hasViolationOn(violations, "workflow.timestampSource"));
    }

    @Test
    void aManagedProfileWithoutAFamilyIsRejected() {
        ContentSigningWorkflowRequestDto workflow = contentSigningWorkflow(SignatureLevel.SIGNED);
        workflow.setFamily(null);

        Set<ConstraintViolation<SigningProfileRequestDto>> violations = validator
                .validate(profileRequest(managedScheme(), workflow));

        assertTrue(hasViolationOn(violations, "workflow.family"));
    }

    @Test
    void aManagedProfileWithoutAMaxLevelIsRejected() {
        Set<ConstraintViolation<SigningProfileRequestDto>> violations = validator
                .validate(profileRequest(managedScheme(), contentSigningWorkflow(null)));

        assertTrue(hasViolationOn(violations, "workflow.maxLevel"));
    }

    /** Pins that both checks run unconditionally, not one short-circuiting the other via {@code &&}. */
    @Test
    void aManagedProfileMissingBothFamilyAndMaxLevelReportsBoth() {
        ContentSigningWorkflowRequestDto workflow = contentSigningWorkflow(null);
        workflow.setFamily(null);

        Set<ConstraintViolation<SigningProfileRequestDto>> violations = validator
                .validate(profileRequest(managedScheme(), workflow));

        assertTrue(hasViolationOn(violations, "workflow.family"));
        assertTrue(hasViolationOn(violations, "workflow.maxLevel"));
    }

    /** Delegated signing does no formatting here, so none of the signature parameters apply to it. */
    @Test
    void aDelegatedProfileNeedsNoSignatureParameters() {
        ContentSigningWorkflowRequestDto workflow = new ContentSigningWorkflowRequestDto();

        Set<ConstraintViolation<SigningProfileRequestDto>> violations = validator
                .validate(profileRequest(delegatedScheme(), workflow));

        assertFalse(hasViolationOn(violations, "workflow.family"));
        assertFalse(hasViolationOn(violations, "workflow.maxLevel"));
    }

    /**
     * The delegated case is what makes the check scheme-aware: a level above SIGNED with no source is a contradiction
     * only under ILM-managed signing. Naming {@code maxLevel} points at the field the caller actually set wrongly,
     * rather than demanding a {@code timestampSource} that delegated signing has no use for.
     */
    @Test
    void aDelegatedProfileCarryingAManagedLevelIsRejectedAtThatField() {
        ContentSigningWorkflowRequestDto workflow = new ContentSigningWorkflowRequestDto();
        workflow.setMaxLevel(SignatureLevel.TIMESTAMPED);

        Set<ConstraintViolation<SigningProfileRequestDto>> violations = validator
                .validate(profileRequest(delegatedScheme(), workflow));

        assertTrue(hasViolationOn(violations, "workflow.maxLevel"));
        assertFalse(hasViolationOn(violations, "workflow.timestampSource"));
    }

    @Test
    void aDelegatedProfileCarryingASignatureFamilyIsRejected() {
        ContentSigningWorkflowRequestDto workflow = new ContentSigningWorkflowRequestDto();
        workflow.setFamily(SignatureFamily.PADES);

        assertTrue(hasViolationOn(validator.validate(profileRequest(delegatedScheme(), workflow)), "workflow.family"));
    }

    /**
     * The formatting connector is as managed-only as the rest: delegated signing formats elsewhere, so a connector
     * bound here would never be called.
     */
    @Test
    void aDelegatedProfileCarryingAFormattingConnectorIsRejected() {
        ContentSigningWorkflowRequestDto workflow = new ContentSigningWorkflowRequestDto();
        workflow.setSignatureFormattingConnectorUuid(UUID.randomUUID());

        assertTrue(hasViolationOn(validator.validate(profileRequest(delegatedScheme(), workflow)),
                "workflow.signatureFormattingConnectorUuid"));
    }

    @Test
    void aDelegatedProfileCarryingFormattingAttributesIsRejected() {
        ContentSigningWorkflowRequestDto workflow = new ContentSigningWorkflowRequestDto();
        workflow.setSignatureFormattingConnectorAttributes(List.of(new RequestAttributeV3()));

        assertTrue(hasViolationOn(validator.validate(profileRequest(delegatedScheme(), workflow)),
                "workflow.signatureFormattingConnectorAttributes"));
    }

    /** The attribute list defaults to an empty one, so an untouched workflow must not trip the rejection. */
    @Test
    void aDelegatedProfileWithTheDefaultAttributeListIsAccepted() {
        Set<ConstraintViolation<SigningProfileRequestDto>> violations = validator
                .validate(profileRequest(delegatedScheme(), new ContentSigningWorkflowRequestDto()));

        assertFalse(hasViolationOn(violations, "workflow.signatureFormattingConnectorAttributes"));
    }

    @Test
    void aManagedProfileAboveSignedNeedsATimestampSource() {
        Set<ConstraintViolation<SigningProfileRequestDto>> violations = validator
                .validate(profileRequest(managedScheme(), contentSigningWorkflow(SignatureLevel.TIMESTAMPED)));

        assertTrue(hasViolationOn(violations, "workflow.timestampSource"));
    }

    @Test
    void aManagedProfileAboveSignedIsAcceptedWithATimestampSource() {
        ContentSigningWorkflowRequestDto workflow = contentSigningWorkflow(SignatureLevel.ARCHIVAL);
        workflow.setTimestampSource(new InternalTimestampSourceRequestDto(UUID.randomUUID()));

        Set<ConstraintViolation<SigningProfileRequestDto>> violations = validator
                .validate(profileRequest(managedScheme(), workflow));

        assertFalse(hasViolationOn(violations, "workflow.timestampSource"));
    }

    /** SIGNED embeds no timestamp, so it is the one level that needs no source. */
    @Test
    void aManagedProfileAtSignedNeedsNoTimestampSource() {
        Set<ConstraintViolation<SigningProfileRequestDto>> violations = validator
                .validate(profileRequest(managedScheme(), contentSigningWorkflow(SignatureLevel.SIGNED)));

        assertFalse(hasViolationOn(violations, "workflow.timestampSource"));
    }

    @Test
    void aNonPositiveDocumentSizeCapIsRejected() {
        ContentSigningWorkflowRequestDto workflow = contentSigningWorkflow(SignatureLevel.SIGNED);
        workflow.setDocumentSizeCap(0L);

        Set<ConstraintViolation<SigningProfileRequestDto>> violations = validator
                .validate(profileRequest(managedScheme(), workflow));

        assertTrue(hasViolationOn(violations, "workflow.documentSizeCap"));

        workflow.setDocumentSizeCap(1L);

        violations = validator.validate(profileRequest(managedScheme(), workflow));

        assertFalse(hasViolationOn(violations, "workflow.documentSizeCap"));
    }
}
