package com.otilm.api.model.client.inspection;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The codes are what a generated client sends and receives, so they are part of the contract rather than an
 * implementation detail of the enum.
 */
class InspectedEntryKindTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @ParameterizedTest
    @EnumSource(InspectedEntryKind.class)
    void everyKindRoundTripsThroughItsCode(InspectedEntryKind kind) throws Exception {
        // given
        String json = MAPPER.writeValueAsString(kind);

        // when
        InspectedEntryKind read = MAPPER.readValue(json, InspectedEntryKind.class);

        // then
        assertEquals('"' + kind.getCode() + '"', json);
        assertSame(kind, read);
        assertSame(kind, InspectedEntryKind.findByCode(kind.getCode()));
    }

    @ParameterizedTest
    @EnumSource(InspectedEntryKind.class)
    void everyKindIsDescribedForSomeoneChoosingBetweenThem(InspectedEntryKind kind) {
        // given
        // when
        // then
        assertFalse(kind.getLabel().isBlank(), kind.name());
        assertFalse(kind.getDescription().isBlank(), kind.name());
    }

    @Test
    void refusesACodeItDoesNotDefine() {
        // given
        String unknown = "keyPairWithoutChain";

        // when
        IllegalArgumentException failure = assertThrows(IllegalArgumentException.class,
                () -> InspectedEntryKind.findByCode(unknown));

        // then
        assertEquals("Unknown inspected entry kind " + unknown, failure.getMessage());
    }
}
