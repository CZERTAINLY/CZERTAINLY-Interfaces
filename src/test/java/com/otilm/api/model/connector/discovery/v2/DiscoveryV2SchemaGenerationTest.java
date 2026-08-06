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
 * D5: verifies, by actually generating the OpenAPI schema (via swagger-core's {@code
 * ModelConverters} — the same annotation-driven resolver springdoc itself uses at runtime; this
 * module has no OpenAPI build plugin to invoke separately), that moving the discriminator inside
 * the payload/event (D1/D2) produces a real {@code discriminator} stanza with a {@code mapping}
 * on each union base, and that the discriminator property is present in every {@code oneOf}
 * subschema — the exact thing a container-level discriminator could never produce, and the reason
 * for this whole rework (see {@code DiscoveredItemPayloadDto} and {@code DiscoveryEvent} javadoc).
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
     * The OpenAPI rule this whole rework exists to satisfy: {@code discriminator.propertyName}
     * must name a property present in the subschema — springdoc/swagger-core would happily
     * generate a discriminator that fails this rule (as the earlier container-level design would
     * have), so this has to be checked against the generated subschema, not assumed.
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
