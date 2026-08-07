package com.otilm.api.model.connector.discovery.v2;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.media.Discriminator;
import io.swagger.v3.oas.models.media.Schema;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Generates the OpenAPI schema and asserts each union base carries a {@code discriminator} stanza
 * with a {@code mapping}, and that the discriminator property appears in every {@code oneOf}
 * subschema — the condition client generators require, and the reason the discriminator sits
 * inside the payload rather than on its container (see {@code DiscoveredItemPayloadDto} and
 * {@code DiscoveryEvent}). Generation goes through swagger-core's {@code ModelConverters}, the
 * resolver springdoc uses at runtime, because this module has no OpenAPI build plugin to invoke.
 */
class DiscoveryV2SchemaGenerationTest {

    @Test
    void discoveredItemPayloadSchemaCarriesADiscriminatorWithMapping() {
        Map<String, Schema> schemas = ModelConverters.getInstance().readAll(DiscoveredItemPayloadDto.class);

        Schema<?> base = schemas.get("DiscoveredItemPayload");
        assertNotNull(base, "expected a generated schema named DiscoveredItemPayload; found " + schemas.keySet());

        Discriminator discriminator = base.getDiscriminator();
        assertNotNull(discriminator, "DiscoveredItemPayload must carry a discriminator stanza");
        assertEquals("resource", discriminator.getPropertyName());
        assertEquals(Map.of(
                        "certificates", "#/components/schemas/DiscoveredCertificateDto",
                        "keys", "#/components/schemas/DiscoveredKeyDto"),
                discriminator.getMapping(),
                "discriminator.mapping must name both concrete payload schemas by their wire codes");

        assertSubschemaDeclaresProperty(schemas, "DiscoveredCertificateDto", "resource");
        assertSubschemaDeclaresProperty(schemas, "DiscoveredKeyDto", "resource");
    }

    @Test
    void discoveryEventSchemaCarriesADiscriminatorWithMapping() {
        Map<String, Schema> schemas = ModelConverters.getInstance().readAll(DiscoveryEvent.class);

        Schema<?> base = schemas.get("DiscoveryEvent");
        assertNotNull(base, "expected a generated schema named DiscoveryEvent; found " + schemas.keySet());

        Discriminator discriminator = base.getDiscriminator();
        assertNotNull(discriminator, "DiscoveryEvent must carry a discriminator stanza");
        assertEquals("type", discriminator.getPropertyName());
        assertEquals(Map.of(
                        "progress", "#/components/schemas/DiscoveryProgressEvent",
                        "resultBatch", "#/components/schemas/DiscoveryResultBatchEvent",
                        "stateChanged", "#/components/schemas/DiscoveryStateChangedEvent",
                        "heartbeat", "#/components/schemas/DiscoveryHeartbeatEvent",
                        "error", "#/components/schemas/DiscoveryErrorEvent"),
                discriminator.getMapping(),
                "discriminator.mapping must name all five concrete event schemas by their wire codes");

        assertSubschemaDeclaresProperty(schemas, "DiscoveryProgressEvent", "type");
        assertSubschemaDeclaresProperty(schemas, "DiscoveryResultBatchEvent", "type");
        assertSubschemaDeclaresProperty(schemas, "DiscoveryStateChangedEvent", "type");
        assertSubschemaDeclaresProperty(schemas, "DiscoveryHeartbeatEvent", "type");
        assertSubschemaDeclaresProperty(schemas, "DiscoveryErrorEvent", "type");
    }

    /**
     * The OpenAPI rule every generated discriminator must satisfy: {@code discriminator.propertyName}
     * must name a property present in the subschema. springdoc/swagger-core will happily generate a
     * discriminator that fails this rule, so it is checked against the generated subschema rather
     * than assumed.
     *
     * <p>Verified empirically that "present" here means "resolvable across the schema graph": each
     * concrete subtype implements the annotated base interface, so swagger-core emits it as a
     * {@code ComposedSchema} — {@code allOf: [{$ref: <base>}, {type: object, properties: {<own
     * fields>}}]} — with the discriminator property living on the referenced base (declared once,
     * as the interface's own abstract accessor) rather than duplicated onto every subtype's local
     * properties. That is the same shape the OpenAPI spec's own Pet/Cat/Dog discriminator example
     * uses, and {@code allOf} composition is exactly how a JSON Schema validator (and codegen)
     * resolves a property "present in" a subschema — so this walks {@code allOf}/{@code $ref}
     * rather than checking {@link Schema#getProperties()} directly.
     */
    private void assertSubschemaDeclaresProperty(Map<String, Schema> schemas, String schemaName, String property) {
        Schema<?> sub = schemas.get(schemaName);
        assertNotNull(sub, "expected a generated schema named " + schemaName + "; found " + schemas.keySet());
        assertTrue(resolvesProperty(sub, property, schemas),
                schemaName + " must resolve property '" + property + "' (directly or via allOf/$ref) for the "
                        + "discriminator to be resolvable against it");
    }

    /**
     * A component must not be published differently depending on which endpoint's schema graph
     * reached it, because the assembled document keeps one definition per name and whichever entry
     * wins is what SDK generators consume.
     *
     * <p>Two ways that broke here. A self-referential {@code byResource} made the event path emit a
     * {@code DiscoveryProgressDto} with {@code byResource} silently missing, and a {@code description}
     * on a {@code $ref} field was hoisted onto the referenced component, overwriting its own — OpenAPI
     * 3.0 cannot carry a sibling description, so swagger-core pushes it down. Both would have shipped
     * a wrong contract to Go and Python connector authors while every Java test stayed green.
     */
    @Test
    void progressComponentsAreIdenticalFromEveryEntryPoint() {
        Map<String, Schema> viaEvent = ModelConverters.getInstance().readAll(DiscoveryEvent.class);
        Map<String, Schema> viaStatus = ModelConverters.getInstance().readAll(DiscoveryStatusResponseDto.class);

        // If the event path emits the run-level component at all, it must be the whole thing.
        Schema<?> progressViaEvent = viaEvent.get("DiscoveryProgressDto");
        if (progressViaEvent != null) {
            assertTrue(resolvesProperty(progressViaEvent, "byResource", viaEvent),
                    "DiscoveryProgressDto reached through DiscoveryEvent must not drop byResource");
        }

        assertTrue(resolvesProperty(viaStatus.get("DiscoveryProgressDto"), "byResource", viaStatus),
                "DiscoveryProgressDto must carry byResource on the status path");

        Schema<?> leafViaEvent = viaEvent.get("DiscoveryResourceProgressDto");
        Schema<?> leafViaStatus = viaStatus.get("DiscoveryResourceProgressDto");
        assertNotNull(leafViaEvent, "the per-resource leaf component must be emitted on the event path");
        assertNotNull(leafViaStatus, "the per-resource leaf component must be emitted on the status path");
        assertEquals(leafViaStatus.getDescription(), leafViaEvent.getDescription(),
                "the leaf component's description must not depend on which endpoint reached it");
        assertEquals(leafViaStatus.getProperties().keySet(), leafViaEvent.getProperties().keySet(),
                "the leaf component's properties must not depend on which endpoint reached it");
        assertTrue(leafViaEvent.getDescription().startsWith("Progress counters"),
                "the leaf must keep its own description, not one hoisted from a referencing field; was: "
                        + leafViaEvent.getDescription());
    }

    @SuppressWarnings("unchecked")
    private boolean resolvesProperty(Schema<?> schema, String property, Map<String, Schema> allSchemas) {
        if (schema == null) {
            return false;
        }
        if (schema.getProperties() != null && schema.getProperties().containsKey(property)) {
            return true;
        }
        if (schema.get$ref() != null) {
            String refName = schema.get$ref().substring(schema.get$ref().lastIndexOf('/') + 1);
            if (resolvesProperty(allSchemas.get(refName), property, allSchemas)) {
                return true;
            }
        }
        if (schema.getAllOf() != null) {
            for (Schema<?> member : (java.util.List<Schema<?>>) (java.util.List<?>) schema.getAllOf()) {
                if (resolvesProperty(member, property, allSchemas)) {
                    return true;
                }
            }
        }
        return false;
    }
}
