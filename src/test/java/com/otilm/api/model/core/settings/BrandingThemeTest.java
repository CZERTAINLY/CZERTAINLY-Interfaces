package com.otilm.api.model.core.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.otilm.api.exception.ValidationException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BrandingThemeTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void serializesEachThemeAsItsCode() throws Exception {
        assertEquals("\"light\"", mapper.writeValueAsString(BrandingTheme.LIGHT));
        assertEquals("\"dark\"", mapper.writeValueAsString(BrandingTheme.DARK));
    }

    @Test
    void deserializesEachThemeFromItsCode() throws Exception {
        assertEquals(BrandingTheme.LIGHT, mapper.readValue("\"light\"", BrandingTheme.class));
        assertEquals(BrandingTheme.DARK, mapper.readValue("\"dark\"", BrandingTheme.class));
    }

    @Test
    void findByCodeRoundTripsAllValues() {
        for (BrandingTheme theme : BrandingTheme.values()) {
            assertEquals(theme, BrandingTheme.findByCode(theme.getCode()), theme.name());
        }
    }

    @Test
    void throwsOnUnknownCode() {
        ValueInstantiationException thrown = assertThrows(ValueInstantiationException.class,
                () -> mapper.readValue("\"system\"", BrandingTheme.class));

        assertInstanceOf(ValidationException.class, thrown.getCause());
        assertThrows(ValidationException.class, () -> BrandingTheme.findByCode("system"));
    }

    @Test
    void populatesLabelAndDescriptionForEveryTheme() {
        for (BrandingTheme theme : BrandingTheme.values()) {
            assertFalse(theme.getLabel().isBlank(), "label missing for " + theme.name());
            assertFalse(theme.getDescription().isBlank(), "description missing for " + theme.name());
        }
    }

    /** findByCode resolves with findFirst, so a duplicate code would be shadowed rather than rejected. */
    @Test
    void themeCodesAreUnique() {
        long distinctCodes = Arrays.stream(BrandingTheme.values()).map(BrandingTheme::getCode).distinct().count();

        assertEquals(BrandingTheme.values().length, distinctCodes);
    }

    /**
     * The branded palettes are the only thing an operator configures. "System light" and "system dark" are client-side
     * presentations of these two, and the platform's own themes need no operator input at all, so neither belongs here.
     */
    @Test
    void offersOnlyTheTwoBrandedPalettes() {
        assertEquals(2, BrandingTheme.values().length);
    }
}
