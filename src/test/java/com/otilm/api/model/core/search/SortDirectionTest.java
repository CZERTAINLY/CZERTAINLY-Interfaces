package com.otilm.api.model.core.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.common.enums.PlatformEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SortDirectionTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @ParameterizedTest
    @EnumSource(SortDirection.class)
    void serializesAsCodeAndReadsBack(SortDirection direction) throws Exception {
        // when
        var json = mapper.writeValueAsString(direction);

        // then — the wire form is the lower-case code, not the Java constant name
        assertEquals("\"" + direction.getCode() + "\"", json);
        assertEquals(direction, mapper.readValue(json, SortDirection.class));
    }

    @ParameterizedTest
    @EnumSource(SortDirection.class)
    void carriesAHumanReadableLabelAndNoDescription(SortDirection direction) {
        assertNotNull(direction.getLabel());
        assertNull(direction.getDescription());
    }

    @Test
    void resolvesFromCode() {
        assertEquals(SortDirection.ASC, SortDirection.fromCode("asc"));
        assertEquals(SortDirection.DESC, SortDirection.fromCode("desc"));
    }

    @Test
    void rejectsUnknownCode() {
        // given — the Java constant name is deliberately not a valid code
        var exception = assertThrows(IllegalArgumentException.class, () -> SortDirection.fromCode("ASC"));

        // then
        assertEquals("Unsupported sort direction ASC.", exception.getMessage());
    }

    @Test
    void isPublishedInThePlatformEnumCatalogue() {
        // the column picker renders the direction labels from GET /v1/enums, so the enum has to be registered there
        assertEquals(SortDirection.class, PlatformEnum.SORT_DIRECTION.getEnumClass());
    }
}
