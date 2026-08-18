package com.otilm.api.model.connector.signatures.contentsigning.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.exception.ValidationException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContentSigningFormattingOperationTest {

    private final ObjectMapper mapper = new ObjectMapper();

    /** The codes are route segments, so a changed one silently repoints a live endpoint. */
    @Test
    void wireCodesArePinned() {
        assertEquals("computeDtbs", ContentSigningFormattingOperation.COMPUTE_DTBS.getCode());
        assertEquals("embedSignatureValue", ContentSigningFormattingOperation.EMBED_SIGNATURE_VALUE.getCode());
        assertEquals("computeSignatureTimestampImprint",
                ContentSigningFormattingOperation.COMPUTE_SIGNATURE_TIMESTAMP_IMPRINT.getCode());
        assertEquals("embedSignatureTimestamp", ContentSigningFormattingOperation.EMBED_SIGNATURE_TIMESTAMP.getCode());
        assertEquals("computeArchiveTimestampImprint",
                ContentSigningFormattingOperation.COMPUTE_ARCHIVE_TIMESTAMP_IMPRINT.getCode());
        assertEquals("embedArchiveTimestamp", ContentSigningFormattingOperation.EMBED_ARCHIVE_TIMESTAMP.getCode());
        assertEquals("extendToLevel", ContentSigningFormattingOperation.EXTEND_TO_LEVEL.getCode());
    }

    @Test
    void theContractHasSevenOperations() {
        assertEquals(7, ContentSigningFormattingOperation.values().length);
    }

    @Test
    void findByCodeRoundTripsAllValues() {
        for (ContentSigningFormattingOperation operation : ContentSigningFormattingOperation.values()) {
            assertEquals(operation, ContentSigningFormattingOperation.findByCode(operation.getCode()),
                    operation.name());
        }
    }

    @Test
    void unknownCodeIsRejected() {
        assertThrows(ValidationException.class, () -> ContentSigningFormattingOperation.findByCode("embedEverything"));
    }

    /** The path variable binds by code, so two operations sharing one would make a route ambiguous. */
    @Test
    void codesAreDistinct() {
        Set<String> codes = Arrays
                .stream(ContentSigningFormattingOperation.values())
                .map(ContentSigningFormattingOperation::getCode)
                .collect(Collectors.toSet());

        assertEquals(ContentSigningFormattingOperation.values().length, codes.size());
    }

    @Test
    void serializesAsItsWireCode() throws Exception {
        assertEquals("\"computeDtbs\"", mapper.writeValueAsString(ContentSigningFormattingOperation.COMPUTE_DTBS));
        assertEquals(ContentSigningFormattingOperation.COMPUTE_DTBS,
                mapper.readValue("\"computeDtbs\"", ContentSigningFormattingOperation.class));
    }

    @Test
    void everyOperationIsLabelledAndDescribed() {
        for (ContentSigningFormattingOperation operation : ContentSigningFormattingOperation.values()) {
            assertTrue(operation.getLabel() != null && !operation.getLabel().isBlank(),
                    operation.name() + " has no label");
            assertTrue(operation.getDescription() != null && !operation.getDescription().isBlank(),
                    operation.name() + " has no description");
        }
    }
}
