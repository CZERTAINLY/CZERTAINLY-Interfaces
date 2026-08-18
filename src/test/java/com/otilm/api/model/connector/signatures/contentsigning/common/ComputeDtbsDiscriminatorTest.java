package com.otilm.api.model.connector.signatures.contentsigning.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.otilm.api.model.common.signature.SignatureFamily;
import com.otilm.api.model.connector.signatures.contentsigning.cades.CadesComputeDtbsRequestDto;
import com.otilm.api.model.connector.signatures.contentsigning.jades.JadesComputeDtbsRequestDto;
import com.otilm.api.model.connector.signatures.contentsigning.pades.PadesComputeDtbsRequestDto;
import com.otilm.api.model.connector.signatures.contentsigning.xades.XadesComputeDtbsRequestDto;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ComputeDtbsDiscriminatorTest {

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private static final Map<String, Class<? extends ComputeDtbsRequestDto>> SUBTYPES = Map
            .of(SignatureFamily.Codes.PADES, PadesComputeDtbsRequestDto.class, SignatureFamily.Codes.XADES,
                    XadesComputeDtbsRequestDto.class, SignatureFamily.Codes.CADES, CadesComputeDtbsRequestDto.class,
                    SignatureFamily.Codes.JADES, JadesComputeDtbsRequestDto.class);

    @Test
    void everySubtypeReportsItsOwnFamily() {
        assertEquals(SignatureFamily.PADES, new PadesComputeDtbsRequestDto().getFamily());
        assertEquals(SignatureFamily.XADES, new XadesComputeDtbsRequestDto().getFamily());
        assertEquals(SignatureFamily.CADES, new CadesComputeDtbsRequestDto().getFamily());
        assertEquals(SignatureFamily.JADES, new JadesComputeDtbsRequestDto().getFamily());
    }

    @Test
    void everySubtypeSerializesItsDiscriminator() throws Exception {
        for (Map.Entry<String, Class<? extends ComputeDtbsRequestDto>> entry : SUBTYPES.entrySet()) {
            ComputeDtbsRequestDto dto = entry.getValue().getDeclaredConstructor().newInstance();

            JsonNode json = mapper.valueToTree(dto);

            assertEquals(entry.getKey(), json.get("family").asText(), entry.getValue().getSimpleName());
        }
    }

    @Test
    void theBaseResolvesEveryFamily() throws Exception {
        for (Map.Entry<String, Class<? extends ComputeDtbsRequestDto>> entry : SUBTYPES.entrySet()) {
            String json = """
                    {
                      "family": "%s",
                      "formattingAttributes": [],
                      "document": {
                        "transferMode": "digestOnly",
                        "documentDigest": "AQID",
                        "digestAlgorithm": "SHA-256"
                      },
                      "signerCertificateChain": ["BAUG"],
                      "signingTime": "2026-08-11T09:30:00Z"
                    }
                    """.formatted(entry.getKey());

            ComputeDtbsRequestDto base = mapper.readValue(json, ComputeDtbsRequestDto.class);

            assertInstanceOf(entry.getValue(), base, entry.getKey());
            assertEquals(entry.getKey(), base.getFamily().getCode());
        }
    }

    @Test
    void roundTripThroughTheBasePreservesTheSubtypeAndItsFields() throws Exception {
        CadesComputeDtbsRequestDto original = new CadesComputeDtbsRequestDto();
        original.setFormattingAttributes(List.of());
        original.setSignerCertificateChain(List.of(new byte[]{4, 5, 6}));
        original.setSigningTime(OffsetDateTime.parse("2026-08-11T09:30:00Z"));
        original.setDocument(new InlineDocumentTransferDto(new byte[]{1, 2, 3}));

        String json = mapper.writeValueAsString(original);
        ComputeDtbsRequestDto deserialized = mapper.readValue(json, ComputeDtbsRequestDto.class);

        assertInstanceOf(CadesComputeDtbsRequestDto.class, deserialized);
        assertEquals(SignatureFamily.CADES, deserialized.getFamily());
        assertEquals(original.getSigningTime(), deserialized.getSigningTime());
        assertEquals(original.getDocument(), deserialized.getDocument());
    }

    @Test
    void unknownFamilyIsRejected() {
        String json = """
                {
                  "family": "pkcs7",
                  "formattingAttributes": []
                }
                """;
        assertThrows(InvalidTypeIdException.class, () -> mapper.readValue(json, ComputeDtbsRequestDto.class));
    }

    @Test
    void missingFamilyIsRejected() {
        String json = """
                {
                  "formattingAttributes": []
                }
                """;
        assertThrows(InvalidTypeIdException.class, () -> mapper.readValue(json, ComputeDtbsRequestDto.class));
    }

    @Test
    void nullFamilyIsRejected() {
        String json = """
                {
                  "family": null,
                  "formattingAttributes": []
                }
                """;
        assertThrows(InvalidTypeIdException.class, () -> mapper.readValue(json, ComputeDtbsRequestDto.class));
    }

    /**
     * The union's discriminator is fixed by the subtype that carries it, so a caller cannot repoint it and produce an
     * instance whose Java type and wire family disagree.
     */
    @Test
    void aSubtypeRefusesToBeRepointedAtAnotherFamily() {
        PadesComputeDtbsRequestDto dto = new PadesComputeDtbsRequestDto();

        assertThrows(IllegalArgumentException.class, () -> dto.setFamily(SignatureFamily.CADES));
        assertEquals(SignatureFamily.PADES, dto.getFamily());
    }

    /** Jackson replays the resolved type id into the setter, so the subtype's own family has to stay acceptable. */
    @Test
    void aSubtypeAcceptsItsOwnFamily() {
        PadesComputeDtbsRequestDto dto = new PadesComputeDtbsRequestDto();

        dto.setFamily(SignatureFamily.PADES);

        assertEquals(SignatureFamily.PADES, dto.getFamily());
    }

    /**
     * Every family the enum knows must be reachable through the union. A family added to the enum but never registered
     * as a subtype would advertise a code no connector could ever be sent a request for.
     */
    @Test
    void everyFamilyInTheEnumIsRegisteredAsASubtype() {
        for (SignatureFamily family : SignatureFamily.values()) {
            assertTrue(SUBTYPES.containsKey(family.getCode()),
                    family.name() + " has no registered computeDtbs subtype");
        }
    }
}
