package com.otilm.api.model.connector.signatures.contentsigning.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The fetch manifest is the platform's only account of traffic the connector made on its own behalf, so a renamed kind
 * would corrupt that record silently.
 */
class FetchedArtifactKindTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void wireCodesArePinned() {
        assertEquals("crl", FetchedArtifactKind.CRL.getCode());
        assertEquals("ocsp", FetchedArtifactKind.OCSP.getCode());
        assertEquals("aiaCertificate", FetchedArtifactKind.AIA_CERTIFICATE.getCode());
    }

    @Test
    void findByCodeRoundTripsAllValues() {
        for (FetchedArtifactKind kind : FetchedArtifactKind.values()) {
            assertEquals(kind, FetchedArtifactKind.findByCode(kind.getCode()), kind.name());
        }
    }

    @Test
    void unknownCodeIsRejected() {
        assertThrows(ValidationException.class, () -> FetchedArtifactKind.findByCode("timestamp"));
    }

    @Test
    void serializesAsItsWireCode() throws Exception {
        assertEquals("\"aiaCertificate\"", mapper.writeValueAsString(FetchedArtifactKind.AIA_CERTIFICATE));
        assertEquals(FetchedArtifactKind.AIA_CERTIFICATE,
                mapper.readValue("\"aiaCertificate\"", FetchedArtifactKind.class));
    }

    @Test
    void everyKindIsLabelledAndDescribed() {
        for (FetchedArtifactKind kind : FetchedArtifactKind.values()) {
            assertTrue(kind.getLabel() != null && !kind.getLabel().isBlank(), kind.name() + " has no label");
            assertTrue(kind.getDescription() != null && !kind.getDescription().isBlank(),
                    kind.name() + " has no description");
        }
    }
}
