package com.otilm.api.model.client.signing.profile.workflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.client.signing.profile.workflow.timestamp.InternalTimestampSourceDto;
import com.otilm.api.model.client.signing.profile.workflow.timestamp.InternalTimestampSourceRequestDto;
import com.otilm.api.model.client.signing.profile.workflow.timestamp.TimestampSourceType;
import com.otilm.api.model.common.NameAndUuidDto;
import com.otilm.api.model.common.signature.SignatureFamily;
import com.otilm.api.model.common.signature.SignatureLevel;
import com.otilm.api.model.common.signature.SignatureParameterGroup;
import com.otilm.api.model.common.signature.parameters.pades.PadesSignatureParametersDto;
import com.otilm.api.model.common.signature.parameters.pades.PadesVisibleSignatureDto;
import com.otilm.api.model.common.signature.parameters.pades.PadesVisibleSignaturePlacementDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContentSigningWorkflowDtoTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    private final ObjectMapper mapper = new ObjectMapper();

    private ContentSigningWorkflowRequestDto fullyConfiguredRequest() {
        ContentSigningWorkflowRequestDto dto = new ContentSigningWorkflowRequestDto();
        dto.setSignatureFormattingConnectorUuid(UUID.randomUUID());
        dto.setFamily(SignatureFamily.PADES);
        dto.setMaxLevel(SignatureLevel.ARCHIVAL);
        dto.setTimestampSource(new InternalTimestampSourceRequestDto(UUID.randomUUID()));
        dto.setDocumentSizeCap(5_242_880L);
        return dto;
    }

    /** The request travels inside a Signing Profile, so it has to survive the polymorphic base, not just itself. */
    @Test
    void theRequestRoundTripsThroughTheWorkflowUnion() throws Exception {
        ContentSigningWorkflowRequestDto original = fullyConfiguredRequest();

        String json = mapper.writeValueAsString(original);
        WorkflowRequestDto decoded = mapper.readValue(json, WorkflowRequestDto.class);

        ContentSigningWorkflowRequestDto workflow = assertInstanceOf(ContentSigningWorkflowRequestDto.class, decoded);
        assertEquals(SigningWorkflowType.CONTENT_SIGNING, workflow.getType());
        assertEquals(SignatureFamily.PADES, workflow.getFamily());
        assertEquals(SignatureLevel.ARCHIVAL, workflow.getMaxLevel());
        assertEquals(5_242_880L, workflow.getDocumentSizeCap());
        assertEquals(TimestampSourceType.INTERNAL, workflow.getTimestampSource().getType());
        assertEquals(original.getTimestampSource(), workflow.getTimestampSource());
    }

    @Test
    void theResponseRoundTripsThroughTheWorkflowUnion() throws Exception {
        ContentSigningWorkflowDto original = new ContentSigningWorkflowDto();
        original.setFamily(SignatureFamily.XADES);
        original.setMaxLevel(SignatureLevel.TIMESTAMPED);
        original.setDocumentSizeCap(1024L);
        original.setTimestampSource(new InternalTimestampSourceDto(new NameAndUuidDto(UUID.randomUUID(), "tsa")));

        WorkflowDto decoded = mapper.readValue(mapper.writeValueAsString(original), WorkflowDto.class);

        ContentSigningWorkflowDto workflow = assertInstanceOf(ContentSigningWorkflowDto.class, decoded);
        assertEquals(SignatureFamily.XADES, workflow.getFamily());
        assertEquals(SignatureLevel.TIMESTAMPED, workflow.getMaxLevel());
        assertEquals(1024L, workflow.getDocumentSizeCap());
        assertEquals(TimestampSourceType.INTERNAL, workflow.getTimestampSource().getType());
        assertEquals(original.getTimestampSource(), workflow.getTimestampSource());
    }

    /** A delegated-signing profile carries none of these fields. */
    @Test
    void everyIlmManagedFieldIsOptionalOnTheWire() throws Exception {
        WorkflowRequestDto decoded = mapper.readValue("{\"type\":\"content_signing\"}", WorkflowRequestDto.class);

        ContentSigningWorkflowRequestDto workflow = assertInstanceOf(ContentSigningWorkflowRequestDto.class, decoded);
        assertNull(workflow.getFamily());
        assertNull(workflow.getMaxLevel());
        assertNull(workflow.getTimestampSource());
        assertNull(workflow.getDocumentSizeCap());
        assertNull(workflow.getAllowedRequestParameterGroups());
        assertNull(workflow.getDefaultSignatureParameters());
    }

    @Test
    void theFamilyAndLevelTravelAsTheirWireCodes() throws Exception {
        String json = mapper.writeValueAsString(fullyConfiguredRequest());

        assertTrue(json.contains("\"family\":\"pades\""), json);
        assertTrue(json.contains("\"maxLevel\":\"archival\""), json);
    }

    @Test
    void theAllowListAndTheDefaultsRoundTripThroughTheWorkflowUnion() throws Exception {
        ContentSigningWorkflowRequestDto original = fullyConfiguredRequest();
        original.setAllowedRequestParameterGroups(Set.of(SignatureParameterGroup.SIGNATURE_CONTEXT));
        PadesSignatureParametersDto defaults = new PadesSignatureParametersDto();
        defaults.setReason("Contract approval");
        original.setDefaultSignatureParameters(defaults);

        String json = mapper.writeValueAsString(original);
        WorkflowRequestDto decoded = mapper.readValue(json, WorkflowRequestDto.class);

        ContentSigningWorkflowRequestDto workflow = assertInstanceOf(ContentSigningWorkflowRequestDto.class, decoded);
        assertEquals(Set.of(SignatureParameterGroup.SIGNATURE_CONTEXT), workflow.getAllowedRequestParameterGroups());
        PadesSignatureParametersDto pades = assertInstanceOf(PadesSignatureParametersDto.class,
                workflow.getDefaultSignatureParameters());
        assertEquals("Contract approval", pades.getReason());
    }

    /** The defaults are the same class the request sends, so they stay self-describing inside the profile too. */
    @Test
    void theDefaultsCarryTheirOwnFamilyOnTheWire() throws Exception {
        ContentSigningWorkflowRequestDto request = fullyConfiguredRequest();
        request.setAllowedRequestParameterGroups(Set.of(SignatureParameterGroup.VISIBLE_SIGNATURE_CONTENT));
        request.setDefaultSignatureParameters(new PadesSignatureParametersDto());

        String json = mapper.writeValueAsString(request);

        assertEquals(2, json.split("\"family\":\"pades\"", -1).length - 1, json);
        assertTrue(json.contains("\"visible_signature_content\""), json);
    }

    /**
     * The defaults are the operator's configuration rather than a caller's input, so nothing else applies the field
     * caps to them; only this cascade holds them to the same contract a request obeys.
     */
    @Test
    void validationCascadesIntoTheDefaults() {
        ContentSigningWorkflowRequestDto request = fullyConfiguredRequest();
        PadesSignatureParametersDto defaults = new PadesSignatureParametersDto();
        defaults.setReason("r".repeat(513));
        request.setDefaultSignatureParameters(defaults);

        Set<ConstraintViolation<ContentSigningWorkflowRequestDto>> violations = VALIDATOR.validate(request);

        assertEquals(1, violations.size());
        assertEquals("defaultSignatureParameters.reason", violations.iterator().next().getPropertyPath().toString());
    }

    /** A {@code @Valid} missing on any nested container silences every constraint below it. */
    @Test
    void validationCascadesToTheDeepestDefault() {
        ContentSigningWorkflowRequestDto request = fullyConfiguredRequest();
        PadesVisibleSignaturePlacementDto placement = new PadesVisibleSignaturePlacementDto();
        placement.setPage(0);
        PadesVisibleSignatureDto visibleSignature = new PadesVisibleSignatureDto();
        visibleSignature.setPlacement(placement);
        PadesSignatureParametersDto defaults = new PadesSignatureParametersDto();
        defaults.setVisibleSignature(visibleSignature);
        request.setDefaultSignatureParameters(defaults);

        Set<ConstraintViolation<ContentSigningWorkflowRequestDto>> violations = VALIDATOR.validate(request);

        assertEquals(1, violations.size());
        assertEquals("defaultSignatureParameters.visibleSignature.placement.page",
                violations.iterator().next().getPropertyPath().toString());
    }

    /**
     * The two fields are orthogonal: a default outside the allow-list is an operator-fixed value, not a
     * misconfiguration, so nothing in the contract may refuse it.
     */
    @Test
    void aDefaultForAGroupOutsideTheAllowListValidates() {
        ContentSigningWorkflowRequestDto request = fullyConfiguredRequest();
        request.setAllowedRequestParameterGroups(Set.of());
        PadesSignatureParametersDto defaults = new PadesSignatureParametersDto();
        defaults.setReason("Fixed by the operator");
        request.setDefaultSignatureParameters(defaults);

        assertTrue(VALIDATOR.validate(request).isEmpty());
    }
}
