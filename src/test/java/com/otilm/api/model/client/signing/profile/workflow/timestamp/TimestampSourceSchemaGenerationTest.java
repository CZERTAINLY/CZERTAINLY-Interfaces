package com.otilm.api.model.client.signing.profile.workflow.timestamp;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.media.Discriminator;
import io.swagger.v3.oas.models.media.Schema;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Schema generation goes through swagger-core's {@code ModelConverters}, the resolver springdoc uses at runtime,
 * because this module has no OpenAPI build plugin to invoke.
 */
class TimestampSourceSchemaGenerationTest {

    private static final String RESPONSE_BASE = "TimestampSourceDto";
    private static final String RESPONSE_ARM = "InternalTimestampSourceDto";
    private static final String REQUEST_BASE = "TimestampSourceRequestDto";
    private static final String REQUEST_ARM = "InternalTimestampSourceRequestDto";

    @Test
    void theResponseUnionCarriesADiscriminatorWithMapping() {
        Schema<?> base = readAll(TimestampSourceDto.class).get(RESPONSE_BASE);
        assertNotNull(base, "expected a generated schema named " + RESPONSE_BASE);

        Discriminator discriminator = base.getDiscriminator();
        assertNotNull(discriminator, RESPONSE_BASE + " must carry a discriminator stanza");
        assertEquals("type", discriminator.getPropertyName());
        assertEquals(Map.of(TimestampSourceType.Codes.INTERNAL, "#/components/schemas/" + RESPONSE_ARM),
                discriminator.getMapping());
    }

    @Test
    void theRequestUnionCarriesADiscriminatorWithMapping() {
        Schema<?> base = readAll(TimestampSourceRequestDto.class).get(REQUEST_BASE);
        assertNotNull(base, "expected a generated schema named " + REQUEST_BASE);

        Discriminator discriminator = base.getDiscriminator();
        assertNotNull(discriminator, REQUEST_BASE + " must carry a discriminator stanza");
        assertEquals("type", discriminator.getPropertyName());
        assertEquals(Map.of(TimestampSourceType.Codes.INTERNAL, "#/components/schemas/" + REQUEST_ARM),
                discriminator.getMapping());
    }

    @Test
    void theResponseUnionListsItsArmInOneOf() {
        Schema<?> base = readAll(TimestampSourceDto.class).get(RESPONSE_BASE);

        assertEquals(List.of("#/components/schemas/" + RESPONSE_ARM),
                base.getOneOf().stream().map(Schema::get$ref).toList());
    }

    @Test
    void theRequestUnionListsItsArmInOneOf() {
        Schema<?> base = readAll(TimestampSourceRequestDto.class).get(REQUEST_BASE);

        assertEquals(List.of("#/components/schemas/" + REQUEST_ARM),
                base.getOneOf().stream().map(Schema::get$ref).toList());
    }

    @Test
    void theResponseArmInheritsTheUnionAndRequiresTheDiscriminator() {
        Schema<?> arm = readAll(TimestampSourceDto.class).get(RESPONSE_ARM);
        assertNotNull(arm, "expected a generated schema named " + RESPONSE_ARM);

        assertTrue(inheritsFrom(arm, RESPONSE_BASE), RESPONSE_ARM + " must allOf-inherit " + RESPONSE_BASE);
        assertTrue(arm.getRequired().contains("type"), RESPONSE_ARM + " must require the type discriminator");
    }

    @Test
    void theRequestArmInheritsTheUnionAndRequiresTheDiscriminator() {
        Schema<?> arm = readAll(TimestampSourceRequestDto.class).get(REQUEST_ARM);
        assertNotNull(arm, "expected a generated schema named " + REQUEST_ARM);

        assertTrue(inheritsFrom(arm, REQUEST_BASE), REQUEST_ARM + " must allOf-inherit " + REQUEST_BASE);
        assertTrue(arm.getRequired().contains("type"), REQUEST_ARM + " must require the type discriminator");
    }

    /** Renaming a published schema breaks every generated client, so the four names are pinned here. */
    @Test
    void theUnionPublishesTheDtoSuffixedNames() {
        assertTrue(readAll(TimestampSourceDto.class).keySet().containsAll(List.of(RESPONSE_BASE, RESPONSE_ARM)),
                "response union publishes " + readAll(TimestampSourceDto.class).keySet());
        assertTrue(readAll(TimestampSourceRequestDto.class).keySet().containsAll(List.of(REQUEST_BASE, REQUEST_ARM)),
                "request union publishes " + readAll(TimestampSourceRequestDto.class).keySet());
    }

    /** {@code ModelConverters.readAll} is declared with a raw {@code Schema}; the cast is contained here. */
    @SuppressWarnings("unchecked")
    private static Map<String, Schema<?>> readAll(Class<?> type) {
        return (Map<String, Schema<?>>) (Map<String, ?>) ModelConverters.getInstance().readAll(type);
    }

    private static boolean inheritsFrom(Schema<?> arm, String baseName) {
        return arm.getAllOf() != null
                && arm.getAllOf().stream().anyMatch(m -> ("#/components/schemas/" + baseName).equals(m.get$ref()));
    }
}
