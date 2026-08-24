package com.otilm.api.model.common.signature.parameters.pades;

import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.common.enums.IPlatformEnum;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PadesParameterEnumsTest {

    @Test
    void theScopeTravelsAsItsCode() {
        assertEquals("certification", PadesSignatureScope.CERTIFICATION.getCode());
        assertEquals("revision", PadesSignatureScope.REVISION.getCode());
        assertEquals(PadesSignatureScope.CERTIFICATION, PadesSignatureScope.findByCode("certification"));
        assertThrows(ValidationException.class, () -> PadesSignatureScope.findByCode("approval"));
    }

    /** A free OID string would let a caller inject arbitrary OIDs into signed attributes; the six values cannot. */
    @Test
    void theSixCommitmentTypesAreTheWholeEnum() {
        assertEquals(6, CommitmentType.values().length);
        assertEquals("proof_of_origin", CommitmentType.PROOF_OF_ORIGIN.getCode());
        assertEquals("proof_of_receipt", CommitmentType.PROOF_OF_RECEIPT.getCode());
        assertEquals("proof_of_delivery", CommitmentType.PROOF_OF_DELIVERY.getCode());
        assertEquals("proof_of_sender", CommitmentType.PROOF_OF_SENDER.getCode());
        assertEquals("proof_of_approval", CommitmentType.PROOF_OF_APPROVAL.getCode());
        assertEquals("proof_of_creation", CommitmentType.PROOF_OF_CREATION.getCode());
        Arrays
                .stream(CommitmentType.values())
                .forEach(type -> assertEquals(type, CommitmentType.findByCode(type.getCode())));
        assertThrows(ValidationException.class, () -> CommitmentType.findByCode("1.2.840.113549.1.9.16.6.1"));
    }

    @Test
    void everyValueCarriesACodeLabelAndDescriptionForTheOperatorScreens() {
        List<IPlatformEnum> values = new ArrayList<>();
        values.addAll(List.of(PadesSignatureScope.values()));
        values.addAll(List.of(CommitmentType.values()));
        values.addAll(List.of(HorizontalAlignment.values()));
        values.addAll(List.of(VerticalAlignment.values()));

        for (IPlatformEnum value : values) {
            assertFalse(value.getCode().isBlank(), value.toString());
            assertFalse(value.getLabel().isBlank(), value.toString());
            assertFalse(value.getDescription().isBlank(), value.toString());
        }
    }

    @Test
    void theAlignmentsTravelAsTheirCodes() {
        assertEquals("left", HorizontalAlignment.LEFT.getCode());
        assertEquals("center", HorizontalAlignment.CENTER.getCode());
        assertEquals("right", HorizontalAlignment.RIGHT.getCode());
        assertEquals("top", VerticalAlignment.TOP.getCode());
        assertEquals("middle", VerticalAlignment.MIDDLE.getCode());
        assertEquals("bottom", VerticalAlignment.BOTTOM.getCode());
        assertEquals(HorizontalAlignment.RIGHT, HorizontalAlignment.findByCode("right"));
        assertEquals(VerticalAlignment.BOTTOM, VerticalAlignment.findByCode("bottom"));
        assertThrows(ValidationException.class, () -> HorizontalAlignment.findByCode("justify"));
        assertThrows(ValidationException.class, () -> VerticalAlignment.findByCode("baseline"));
    }
}
