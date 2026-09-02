package com.otilm.api.model.core.branding;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.core.settings.BrandingSettingsDto;
import com.otilm.api.model.core.settings.BrandingTheme;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.media.Schema;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PublicBrandingDtoTest {

    /** Every stored branding field except the one the response omits rather than nulls. */
    private static final List<String> ALWAYS_PRESENT_FIELDS = List
            .of("primaryColor", "secondaryColor", "tertiaryColor", "backgroundColor", "textColor", "lightLogo",
                    "darkLogo");

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
        assertEquals(storedFields.keySet().stream().filter(name -> !"defaultTheme".equals(name)).toList(),
                ALWAYS_PRESENT_FIELDS, "ALWAYS_PRESENT_FIELDS has drifted from the stored branding fields");
    }

    /**
     * A client reads this once before it has any session, so the response keeps a fixed shape rather than omitting
     * unset fields: "no logo configured" and "response not understood" must not look alike.
     * <p>
     * Serialized through a mapper configured the way Core's web {@code ObjectMapper} is — {@code NON_NULL} — rather
     * than a default one. A default mapper already includes nulls, so it would pass whether or not the DTO declares
     * {@code @JsonInclude(ALWAYS)}, and the production shape would be the only thing left untested.
     */
    @Test
    void serializesUnsetBrandingAsExplicitNullsEvenWhenTheMapperOmitsNulls() throws Exception {
        ObjectMapper nullOmittingMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        String json = nullOmittingMapper.writeValueAsString(new PublicBrandingDto());

        assertTrue(json.contains("\"configured\":false"), json);
        for (String field : ALWAYS_PRESENT_FIELDS) {
            assertTrue(json.contains("\"%s\":null".formatted(field)), field + " omitted from " + json);
        }
    }

    /**
     * {@code defaultTheme} is the exception to the fixed shape, and the exception is deliberate: it resolves to a
     * {@code $ref} on the shared {@code BrandingTheme} schema, which neither swagger-core nor springdoc can mark
     * nullable without emitting a sibling {@code type} that contradicts the referenced enum. Absent-when-unset is
     * representable in every generated client; {@code null} on a required {@code $ref} is representable in none.
     */
    @Test
    void omitsTheDefaultThemeRatherThanSendingANullRef() throws Exception {
        ObjectMapper nullOmittingMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        String json = nullOmittingMapper.writeValueAsString(new PublicBrandingDto());

        assertFalse(json.contains("defaultTheme"), json);
    }

    /**
     * The serialized shape and the published schema have to agree, or a generated client cannot represent the very
     * first response it receives. Every always-present field is declared {@code required} — it is never omitted — and
     * typed {@code ["string", "null"]}, because on an unconfigured platform every one of them arrives as {@code null}.
     * Declaring them optional-and-non-nullable, which is what a bare {@code @Schema} would produce, describes a
     * response this endpoint never sends.
     * <p>
     * Generation goes through swagger-core's {@code ModelConverters}, the resolver springdoc uses at runtime, since
     * this module has no OpenAPI build plugin to invoke.
     */
    @Test
    void publishesTheAlwaysPresentFieldsAsRequiredAndNullable() {
        Schema<?> schema = ModelConverters.getInstance(true).readAllAsResolvedSchema(PublicBrandingDto.class).schema;

        assertEquals(Stream.concat(Stream.of("configured"), ALWAYS_PRESENT_FIELDS.stream()).collect(Collectors.toSet()),
                Set.copyOf(schema.getRequired()));

        for (String field : ALWAYS_PRESENT_FIELDS) {
            Schema<?> property = (Schema<?>) schema.getProperties().get(field);

            assertEquals(Set.of("string", "null"), property.getTypes(), field + " is not declared nullable");
        }
    }

    /** The other half of the same rule: a field that can be omitted must not be published as required. */
    @Test
    void doesNotPublishTheOmittableDefaultThemeAsRequired() {
        Schema<?> schema = ModelConverters.getInstance(true).readAllAsResolvedSchema(PublicBrandingDto.class).schema;

        assertFalse(schema.getRequired().contains("defaultTheme"), schema.getRequired().toString());
    }

    @Test
    void serializesTheDefaultThemeAsItsCode() throws Exception {
        PublicBrandingDto dto = new PublicBrandingDto();
        dto.setConfigured(true);
        dto.setDefaultTheme(BrandingTheme.DARK);

        assertTrue(mapper.writeValueAsString(dto).contains("\"defaultTheme\":\"dark\""));
    }
}
