package com.otilm.api.model.core.branding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.core.settings.BrandingSettingsDto;
import com.otilm.api.model.core.settings.BrandingTheme;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PublicBrandingDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    private static Map<String, Class<?>> declaredFields(Class<?> type) {
        Map<String, Class<?>> fields = new LinkedHashMap<>();
        Arrays
                .stream(type.getDeclaredFields())
                .filter(field -> !field.isSynthetic())
                .forEach(field -> fields.put(field.getName(), field.getType()));
        return fields;
    }

    /**
     * The whole point of a separate type: inheriting or embedding a settings DTO would serve every field later added to
     * platform settings to anonymous callers, without anyone having decided to.
     */
    @Test
    void doesNotInheritFromAnySettingsDto() {
        assertEquals(Object.class, PublicBrandingDto.class.getSuperclass());
        assertFalse(BrandingSettingsDto.class.isAssignableFrom(PublicBrandingDto.class));
    }

    @Test
    void embedsNoSettingsDto() {
        for (Field field : PublicBrandingDto.class.getDeclaredFields()) {
            assertFalse(field.getType().getName().contains(".settings.") && field.getType() != BrandingTheme.class,
                    "field " + field.getName() + " exposes a settings type on the anonymous path");
        }
    }

    /**
     * Duplicated fields drift, so the two shapes are pinned to each other here: the public response carries exactly the
     * stored branding plus the {@code configured} flag, and adding a field to one without the other fails.
     */
    @Test
    void carriesTheStoredBrandingFieldsPlusTheConfiguredFlag() {
        Map<String, Class<?>> publicFields = declaredFields(PublicBrandingDto.class);
        Map<String, Class<?>> storedFields = declaredFields(BrandingSettingsDto.class);

        assertEquals(boolean.class, publicFields.remove("configured"), "configured flag missing or retyped");
        assertEquals(storedFields, publicFields);
    }

    /**
     * A client reads this once before it has any session, so the response keeps a fixed shape rather than omitting
     * unset fields: "no logo configured" and "response not understood" must not look alike.
     */
    @Test
    void serializesUnsetBrandingAsExplicitNulls() throws Exception {
        String json = mapper.writeValueAsString(new PublicBrandingDto());

        assertTrue(json.contains("\"configured\":false"), json);
        for (String field : declaredFields(BrandingSettingsDto.class).keySet()) {
            assertTrue(json.contains("\"%s\":null".formatted(field)), field + " omitted from " + json);
        }
    }

    @Test
    void serializesTheDefaultThemeAsItsCode() throws Exception {
        PublicBrandingDto dto = new PublicBrandingDto();
        dto.setConfigured(true);
        dto.setDefaultTheme(BrandingTheme.DARK);

        assertTrue(mapper.writeValueAsString(dto).contains("\"defaultTheme\":\"dark\""));
    }
}
