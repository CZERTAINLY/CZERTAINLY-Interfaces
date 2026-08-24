package com.otilm.api.model.core.discovery;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.otilm.api.exception.ValidationException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Covers {@link DiscoveryMessageDto}: every field round-trips under its published name, severity travels as a wire code
 * and refuses an unknown one rather than nulling it, and a problem seen once still carries both timestamps rather than
 * leaving the second absent.
 */
class DiscoveryMessageDtoTest {

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void roundTripsEveryField() throws Exception {
        OffsetDateTime first = OffsetDateTime.of(2026, 8, 24, 9, 15, 0, 0, ZoneOffset.UTC);
        DiscoveryMessageDto dto = new DiscoveryMessageDto();
        dto.setSeverity(DiscoveryMessageSeverity.WARNING);
        dto.setCode("ITEM_WITHOUT_SEQUENCE");
        dto.setMessage("3 item(s) arrived without a sequence and were skipped");
        dto.setOccurrences(30000L);
        dto.setFirstSeenAt(first);
        dto.setLastSeenAt(first.plusMinutes(42));

        String json = mapper.writeValueAsString(dto);
        DiscoveryMessageDto back = mapper.readValue(json, DiscoveryMessageDto.class);

        // pinned by literal name: a round-trip alone would survive a rename, since it renames both ends at once
        assertTrue(json.contains("\"severity\":"), json);
        assertTrue(json.contains("\"code\":"), json);
        assertTrue(json.contains("\"message\":"), json);
        assertTrue(json.contains("\"occurrences\":"), json);
        assertTrue(json.contains("\"firstSeenAt\":"), json);
        assertTrue(json.contains("\"lastSeenAt\":"), json);

        assertEquals(DiscoveryMessageSeverity.WARNING, back.getSeverity());
        assertEquals("ITEM_WITHOUT_SEQUENCE", back.getCode());
        assertEquals("3 item(s) arrived without a sequence and were skipped", back.getMessage());
        assertEquals(30000L, back.getOccurrences());
        assertEquals(first, back.getFirstSeenAt());
        assertEquals(first.plusMinutes(42), back.getLastSeenAt());
    }

    @Test
    void severityTravelsAsAWireCode() throws Exception {
        DiscoveryMessageDto dto = new DiscoveryMessageDto();
        dto.setSeverity(DiscoveryMessageSeverity.WARNING);

        String json = mapper.writeValueAsString(dto);

        // The code is the contract, not the Java member name, so a rename of the constant cannot move the wire.
        assertTrue(json.contains("\"severity\":\"warning\""), json);
        assertFalse(json.contains("WARNING"), json);
        assertEquals(DiscoveryMessageSeverity.WARNING, mapper.readValue(json, DiscoveryMessageDto.class).getSeverity());
    }

    @Test
    void unknownSeverityCodeIsRejectedRatherThanNulled() {
        // Reading an unrecognised level as null would drop the field a client renders on, and the one a
        // terminal status decision reads on the storage side. @JsonCreator routes deserialization through here.
        assertThrows(ValidationException.class, () -> DiscoveryMessageSeverity.findByCode("catastrophic"));
    }

    @Test
    void problemSeenOnceCarriesBothTimestamps() throws Exception {
        // lastSeenAt equal to firstSeenAt, never absent: a client rendering a time range must not have to
        // special-case the single-occurrence entry, which is the common one on a healthy run.
        OffsetDateTime seen = OffsetDateTime.of(2026, 8, 24, 9, 15, 0, 0, ZoneOffset.UTC);
        DiscoveryMessageDto dto = new DiscoveryMessageDto();
        dto.setSeverity(DiscoveryMessageSeverity.ERROR);
        dto.setCode("CONNECTOR_ERROR");
        dto.setMessage("The Discovery Provider reported a problem reaching one host");
        dto.setOccurrences(1L);
        dto.setFirstSeenAt(seen);
        dto.setLastSeenAt(seen);

        DiscoveryMessageDto back = mapper.readValue(mapper.writeValueAsString(dto), DiscoveryMessageDto.class);

        assertEquals(1L, back.getOccurrences());
        assertEquals(back.getFirstSeenAt(), back.getLastSeenAt());
    }
}
