package com.otilm.api.model.common.signature.parameters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.otilm.api.model.common.signature.SignatureFamily;
import com.otilm.api.model.common.signature.parameters.pades.CommitmentType;
import com.otilm.api.model.common.signature.parameters.pades.PadesSignatureParametersDto;
import com.otilm.api.model.common.signature.parameters.pades.PadesSignatureScope;
import com.otilm.api.model.common.signature.parameters.pades.PadesVisibleSignatureDto;
import com.otilm.api.model.common.signature.parameters.pades.PadesVisibleSignaturePlacementDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PadesSignatureParametersDtoTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    private final ObjectMapper mapper = new ObjectMapper();

    private static PadesSignatureParametersDto fullyPopulated() {
        PadesSignatureParametersDto parameters = new PadesSignatureParametersDto();
        parameters.setReason("Contract approval");
        parameters.setLocation("Prague");
        parameters.setContactInfo("jane.doe@example.com");
        parameters.setSignerName("Jane Doe");
        parameters.setSignatureScope(PadesSignatureScope.REVISION);
        parameters.setCommitmentType(CommitmentType.PROOF_OF_APPROVAL);
        parameters.setClaimedRoles(List.of("Head of Legal"));

        PadesVisibleSignaturePlacementDto placement = new PadesVisibleSignaturePlacementDto();
        placement.setPage(1);
        placement.setOriginX(20f);
        placement.setOriginY(30f);
        placement.setWidth(180f);
        placement.setHeight(60f);

        PadesVisibleSignatureDto visibleSignature = new PadesVisibleSignatureDto();
        visibleSignature.setVisible(true);
        visibleSignature.setText("Signed by Jane Doe");
        visibleSignature.setPlacement(placement);
        parameters.setVisibleSignature(visibleSignature);
        return parameters;
    }

    @Test
    void theSubtypePinsItsOwnFamily() {
        assertEquals(SignatureFamily.PADES, new PadesSignatureParametersDto().getFamily());
    }

    /** The union resolves on family, so a repointed discriminator would produce an object of a lying type. */
    @Test
    void theDiscriminatorCannotBeRepointed() {
        PadesSignatureParametersDto parameters = new PadesSignatureParametersDto();
        assertThrows(IllegalArgumentException.class, () -> parameters.setFamily(SignatureFamily.XADES));
        parameters.setFamily(SignatureFamily.PADES);
        assertEquals(SignatureFamily.PADES, parameters.getFamily());
    }

    @Test
    void theParametersRoundTripThroughTheUnionBase() throws Exception {
        String json = mapper.writeValueAsString(fullyPopulated());
        assertTrue(json.contains("\"family\":\"pades\""), json);

        SignatureParametersDto decoded = mapper.readValue(json, SignatureParametersDto.class);

        PadesSignatureParametersDto parameters = assertInstanceOf(PadesSignatureParametersDto.class, decoded);
        assertEquals("Contract approval", parameters.getReason());
        assertEquals("Prague", parameters.getLocation());
        assertEquals("jane.doe@example.com", parameters.getContactInfo());
        assertEquals("Jane Doe", parameters.getSignerName());
        assertEquals(PadesSignatureScope.REVISION, parameters.getSignatureScope());
        assertEquals(CommitmentType.PROOF_OF_APPROVAL, parameters.getCommitmentType());
        assertEquals(List.of("Head of Legal"), parameters.getClaimedRoles());
        assertEquals("Signed by Jane Doe", parameters.getVisibleSignature().getText());
        assertEquals(1, parameters.getVisibleSignature().getPlacement().getPage());
    }

    @Test
    void anEmptyObjectIsValidBecauseEveryParameterIsAnOverride() {
        PadesSignatureParametersDto parameters = new PadesSignatureParametersDto();
        assertTrue(VALIDATOR.validate(parameters).isEmpty());
        assertEquals(SignatureFamily.PADES, parameters.getFamily());
    }

    @Test
    void anUnregisteredFamilyIsRejectedByTheUnion() {
        assertThrows(InvalidTypeIdException.class,
                () -> mapper.readValue("{\"family\":\"xades\"}", SignatureParametersDto.class));
    }

    @Test
    void theCapsOnEveryTextParameterAreEnforced() {
        Stream
                .of("reason", "location", "contactInfo", "signerName")
                .forEach(field -> assertFalse(VALIDATOR.validate(overLongText(field)).isEmpty(), field));
    }

    private static PadesSignatureParametersDto overLongText(String field) {
        PadesSignatureParametersDto parameters = new PadesSignatureParametersDto();
        String tooLong = "x".repeat(513);
        switch (field) {
            case "reason" -> parameters.setReason(tooLong);
            case "location" -> parameters.setLocation(tooLong);
            case "contactInfo" -> parameters.setContactInfo(tooLong);
            default -> parameters.setSignerName(tooLong);
        }
        return parameters;
    }

    @Test
    void theClaimedRolesAreCappedInCountAndInLength() {
        PadesSignatureParametersDto tooMany = new PadesSignatureParametersDto();
        tooMany.setClaimedRoles(List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"));
        assertFalse(VALIDATOR.validate(tooMany).isEmpty());

        PadesSignatureParametersDto tooLong = new PadesSignatureParametersDto();
        tooLong.setClaimedRoles(List.of("r".repeat(257)));
        assertFalse(VALIDATOR.validate(tooLong).isEmpty());

        PadesSignatureParametersDto atTheCap = new PadesSignatureParametersDto();
        atTheCap.setClaimedRoles(List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"));
        assertTrue(VALIDATOR.validate(atTheCap).isEmpty());
    }

    /** The whole request prints no caller content by construction, and these parameters carry text and an address. */
    @Test
    void theCallerSuppliedTextStaysOutOfToString() {
        String printed = fullyPopulated().toString();

        assertFalse(printed.contains("Contract approval"), printed);
        assertFalse(printed.contains("Prague"), printed);
        assertFalse(printed.contains("jane.doe@example.com"), printed);
        assertFalse(printed.contains("Jane Doe"), printed);
        assertFalse(printed.contains("Head of Legal"), printed);
        assertTrue(printed.contains("signatureScope=REVISION"), printed);
    }

    /** Named-field mode is the one placement shape carrying caller text, so the coordinate fixture cannot cover it. */
    @Test
    void theNamedFieldStaysOutOfToString() {
        PadesVisibleSignaturePlacementDto placement = new PadesVisibleSignaturePlacementDto();
        placement.setFieldId("Signature1");
        PadesVisibleSignatureDto visibleSignature = new PadesVisibleSignatureDto();
        visibleSignature.setPlacement(placement);
        PadesSignatureParametersDto parameters = new PadesSignatureParametersDto();
        parameters.setVisibleSignature(visibleSignature);

        assertFalse(parameters.toString().contains("Signature1"), parameters.toString());
    }

    /** A blank name counts as present for the merge, so it would override a profile default with an empty /Name. */
    @Test
    void aBlankSignerNameIsRejected() {
        PadesSignatureParametersDto parameters = new PadesSignatureParametersDto();
        parameters.setSignerName("   ");
        assertFalse(VALIDATOR.validate(parameters).isEmpty());
    }

    /** An empty role would be bound into the signature as a signed attribute that says nothing. */
    @Test
    void aBlankClaimedRoleIsRejected() {
        PadesSignatureParametersDto parameters = new PadesSignatureParametersDto();
        parameters.setClaimedRoles(List.of("Head of Legal", ""));

        Set<ConstraintViolation<PadesSignatureParametersDto>> violations = VALIDATOR.validate(parameters);

        assertEquals(1, violations.size());
        assertEquals("each claimed role must not be blank", violations.iterator().next().getMessage());
    }

    /** The nested objects carry their own constraints, which only a cascade reaches. */
    @Test
    void validationCascadesIntoTheVisibleSignatureAndItsPlacement() {
        PadesSignatureParametersDto parameters = new PadesSignatureParametersDto();
        PadesVisibleSignatureDto visibleSignature = new PadesVisibleSignatureDto();
        visibleSignature.setText("t".repeat(4097));
        parameters.setVisibleSignature(visibleSignature);
        assertFalse(VALIDATOR.validate(parameters).isEmpty());

        PadesSignatureParametersDto mixedPlacement = new PadesSignatureParametersDto();
        PadesVisibleSignatureDto withPlacement = new PadesVisibleSignatureDto();
        PadesVisibleSignaturePlacementDto placement = new PadesVisibleSignaturePlacementDto();
        placement.setFieldId("Signature1");
        placement.setPage(2);
        withPlacement.setPlacement(placement);
        mixedPlacement.setVisibleSignature(withPlacement);
        assertFalse(VALIDATOR.validate(mixedPlacement).isEmpty());
    }
}
