package com.otilm.api.model.connector.signatures.contentsigning.pades;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.otilm.api.model.common.signature.SignatureFamily;
import com.otilm.api.model.common.signature.parameters.pades.PadesSignatureParametersDto;
import com.otilm.api.model.connector.signatures.contentsigning.common.ComputeDtbsRequestDto;
import com.otilm.api.model.connector.signatures.contentsigning.common.InlineDocumentTransferDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PadesComputeDtbsRequestDtoTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private static PadesComputeDtbsRequestDto padesRequest() {
        PadesComputeDtbsRequestDto request = new PadesComputeDtbsRequestDto();
        request.setDocument(new InlineDocumentTransferDto(new byte[]{1, 2, 3}));
        request.setSignerCertificateChain(List.of(new byte[]{9}));
        request.setSigningTime(OffsetDateTime.parse("2026-08-11T10:15:30Z"));
        request.setFormattingAttributes(List.of());
        return request;
    }

    /** The field is additive and optional, so an existing connector's payload keeps validating unchanged. */
    @Test
    void theParametersAreOptional() {
        PadesComputeDtbsRequestDto request = padesRequest();
        assertNull(request.getSignatureParameters());
        assertTrue(VALIDATOR.validate(request).isEmpty());
    }

    /** Non-Java connectors read the key, so it must appear even when the platform sends no parameters. */
    @Test
    void theAbsentParametersStillAppearOnTheWire() throws Exception {
        String json = mapper.writeValueAsString(padesRequest());

        assertTrue(json.contains("\"signatureParameters\":null"), json);
    }

    @Test
    void theParametersRoundTripThroughTheRequestUnion() throws Exception {
        PadesComputeDtbsRequestDto request = padesRequest();
        PadesSignatureParametersDto parameters = new PadesSignatureParametersDto();
        parameters.setReason("Contract approval");
        request.setSignatureParameters(parameters);

        String json = mapper.writeValueAsString(request);
        ComputeDtbsRequestDto decoded = mapper.readValue(json, ComputeDtbsRequestDto.class);

        PadesComputeDtbsRequestDto pades = assertInstanceOf(PadesComputeDtbsRequestDto.class, decoded);
        assertEquals(SignatureFamily.PADES, pades.getFamily());
        assertEquals("Contract approval", pades.getSignatureParameters().getReason());
    }

    /** The parameters object stays self-describing wherever it travels, so it carries its own discriminator. */
    @Test
    void theParametersCarryTheirOwnFamilyOnTheWire() throws Exception {
        PadesComputeDtbsRequestDto request = padesRequest();
        request.setSignatureParameters(new PadesSignatureParametersDto());

        String json = mapper.writeValueAsString(request);

        assertEquals(2, json.split("\"family\":\"pades\"", -1).length - 1, json);
    }

    @Test
    void aParametersObjectOfAnotherFamilyIsRejected() {
        String json = "{\"family\":\"pades\",\"signatureParameters\":{\"family\":\"xades\"}}";
        assertThrows(InvalidTypeIdException.class, () -> mapper.readValue(json, ComputeDtbsRequestDto.class));
    }

    /** The union has no defaultImpl on purpose. */
    @Test
    void aParametersObjectWithoutItsFamilyIsRejected() {
        String json = "{\"family\":\"pades\",\"signatureParameters\":{\"reason\":\"x\"}}";
        assertThrows(InvalidTypeIdException.class, () -> mapper.readValue(json, ComputeDtbsRequestDto.class));
    }

    @Test
    void validationCascadesIntoTheParameters() {
        PadesComputeDtbsRequestDto request = padesRequest();
        PadesSignatureParametersDto parameters = new PadesSignatureParametersDto();
        parameters.setReason("r".repeat(513));
        request.setSignatureParameters(parameters);

        Set<ConstraintViolation<PadesComputeDtbsRequestDto>> violations = VALIDATOR.validate(request);

        assertEquals(1, violations.size());
        assertEquals("signatureParameters.reason", violations.iterator().next().getPropertyPath().toString());
    }
}
