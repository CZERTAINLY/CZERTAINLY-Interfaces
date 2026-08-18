package com.otilm.api.model.connector.signatures.contentsigning.common;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.media.Discriminator;
import io.swagger.v3.oas.models.media.Schema;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Generates the OpenAPI schema for the {@code computeDtbs} union and asserts it carries a {@code discriminator} stanza
 * with a complete {@code mapping}, and that the discriminator property appears in every {@code oneOf} subschema.
 *
 * <p>
 * Generation goes through swagger-core's {@code ModelConverters}, the resolver springdoc uses at runtime, because this
 * module has no OpenAPI build plugin to invoke.
 * </p>
 */
class ComputeDtbsSchemaGenerationTest {

    private static final String BASE = "ComputeDtbsRequest";

    @Test
    void theComputeDtbsUnionCarriesADiscriminatorWithMapping() {
        Map<String, Schema> schemas = ModelConverters.getInstance().readAll(ComputeDtbsInterface.class);

        Schema<?> base = schemas.get(BASE);
        assertNotNull(base, "expected a generated schema named " + BASE + "; found " + schemas.keySet());

        Discriminator discriminator = base.getDiscriminator();
        assertNotNull(discriminator, BASE + " must carry a discriminator stanza");
        assertEquals("family", discriminator.getPropertyName());
        assertEquals(
                Map
                        .of("pades", "#/components/schemas/PadesComputeDtbsRequestDto", "xades",
                                "#/components/schemas/XadesComputeDtbsRequestDto", "cades",
                                "#/components/schemas/CadesComputeDtbsRequestDto", "jades",
                                "#/components/schemas/JadesComputeDtbsRequestDto"),
                discriminator.getMapping(), "discriminator.mapping must name all four family schemas by wire code");
    }

    @Test
    void everySubschemaDeclaresTheDiscriminatorProperty() {
        Map<String, Schema> schemas = ModelConverters.getInstance().readAll(ComputeDtbsInterface.class);

        for (String subtype : List
                .of("PadesComputeDtbsRequestDto", "XadesComputeDtbsRequestDto", "CadesComputeDtbsRequestDto",
                        "JadesComputeDtbsRequestDto")) {
            Schema<?> schema = schemas.get(subtype);
            assertNotNull(schema, "expected a generated schema named " + subtype + "; found " + schemas.keySet());
            assertTrue(resolvesProperty(schema, "family", schemas),
                    subtype + " does not declare the family discriminator property");
        }
    }

    /**
     * A subtype must also carry the fields the base declares, or a generated client would emit a request missing them.
     */
    @Test
    void everySubschemaCarriesTheSharedRequestFields() {
        Map<String, Schema> schemas = ModelConverters.getInstance().readAll(ComputeDtbsInterface.class);

        for (String subtype : List
                .of("PadesComputeDtbsRequestDto", "XadesComputeDtbsRequestDto", "CadesComputeDtbsRequestDto",
                        "JadesComputeDtbsRequestDto")) {
            Schema<?> schema = schemas.get(subtype);
            for (String property : List
                    .of("document", "signerCertificateChain", "signingTime", "formattingAttributes")) {
                assertTrue(resolvesProperty(schema, property, schemas),
                        subtype + " does not resolve the inherited property " + property);
            }
        }
    }

    /**
     * The document transport is one component reached from several places.
     */
    @Test
    void documentTransferIsPublishedIdenticallyFromEveryEntryPoint() {
        Schema<?> viaCompute = ModelConverters
                .getInstance()
                .readAll(ComputeDtbsInterface.class)
                .get("DocumentTransfer");
        Schema<?> viaSignedDocument = ModelConverters
                .getInstance()
                .readAll(SignedDocumentRequestDto.class)
                .get("DocumentTransfer");
        Schema<?> standalone = ModelConverters.getInstance().readAll(DocumentTransferDto.class).get("DocumentTransfer");

        assertNotNull(viaCompute, "DocumentTransfer not reached from the computeDtbs union");
        assertNotNull(viaSignedDocument, "DocumentTransfer not reached from SignedDocumentRequestDto");
        assertNotNull(standalone, "DocumentTransfer not generated on its own");

        assertNotNull(standalone.getDescription(), "DocumentTransfer publishes no description at all");
        assertFalse(standalone.getDescription().isBlank(), "DocumentTransfer publishes a blank description");

        assertEquals(standalone.getDescription(), viaCompute.getDescription(),
                "DocumentTransfer's description was rewritten when reached through the computeDtbs union");
        assertEquals(standalone.getDescription(), viaSignedDocument.getDescription(),
                "DocumentTransfer's description was rewritten when reached through SignedDocumentRequestDto");
        assertEquals(standalone.getProperties().keySet(), viaCompute.getProperties().keySet());
        assertEquals(standalone.getProperties().keySet(), viaSignedDocument.getProperties().keySet());
    }

    /**
     * The transport choice is a discriminated union, so a generated client picks an arm by reading {@code transferMode}
     * rather than by testing which of three optional fields happens to be set.
     */
    @Test
    void documentTransferPublishesItsTransportChoiceAsADiscriminatedUnion() {
        Map<String, Schema> schemas = ModelConverters.getInstance().readAll(DocumentTransferDto.class);
        Schema<?> transfer = schemas.get("DocumentTransfer");

        assertEquals(List
                .of("#/components/schemas/InlineDocumentTransfer", "#/components/schemas/DigestOnlyDocumentTransfer"),
                transfer.getOneOf().stream().map(Schema::get$ref).toList(),
                "DocumentTransfer must publish the transport choice as oneOf over the two arms");

        Discriminator discriminator = transfer.getDiscriminator();
        assertNotNull(discriminator, "DocumentTransfer must carry a discriminator stanza");
        assertEquals("transferMode", discriminator.getPropertyName());
        assertEquals(
                Map
                        .of("inline", "#/components/schemas/InlineDocumentTransfer", "digestOnly",
                                "#/components/schemas/DigestOnlyDocumentTransfer"),
                discriminator.getMapping(), "discriminator.mapping must name both arms by wire code");
    }

    /**
     * Each arm owns the fields its transport needs, which is what makes a transfer carrying both — or neither — an
     * unrepresentable body rather than one refused after binding.
     */
    @Test
    void eachDocumentTransferArmCarriesOnlyItsOwnFields() {
        Map<String, Schema> schemas = ModelConverters.getInstance().readAll(DocumentTransferDto.class);

        assertEquals(List.of("document", "transferMode"), schemas.get("InlineDocumentTransfer").getRequired());
        assertEquals(List.of("digestAlgorithm", "documentDigest", "transferMode"),
                schemas.get("DigestOnlyDocumentTransfer").getRequired());

        assertTrue(resolvesProperty(schemas.get("InlineDocumentTransfer"), "document", schemas));
        assertFalse(resolvesProperty(schemas.get("InlineDocumentTransfer"), "documentDigest", schemas),
                "the inline arm must not offer a digest field at all");
        assertTrue(resolvesProperty(schemas.get("DigestOnlyDocumentTransfer"), "documentDigest", schemas));
        assertTrue(resolvesProperty(schemas.get("DigestOnlyDocumentTransfer"), "digestAlgorithm", schemas));
        assertFalse(resolvesProperty(schemas.get("DigestOnlyDocumentTransfer"), "document", schemas),
                "the digest arm must not offer an inline document field at all");
    }

    /** An unsuppressed boolean validation getter reaches the wire as a property a generated client tries to send. */
    @Test
    void neitherArmPublishesAnythingBeyondItsOwnFields() {
        Map<String, Schema> schemas = ModelConverters.getInstance().readAll(DocumentTransferDto.class);

        assertEquals(Set.of("document", "transferMode"),
                resolvedProperties(schemas.get("InlineDocumentTransfer"), schemas));
        assertEquals(Set.of("documentDigest", "digestAlgorithm", "transferMode"),
                resolvedProperties(schemas.get("DigestOnlyDocumentTransfer"), schemas));
    }

    /** The discriminator property must be resolvable from every arm, or a generated client cannot set it. */
    @Test
    void everyDocumentTransferArmDeclaresTheDiscriminatorProperty() {
        Map<String, Schema> schemas = ModelConverters.getInstance().readAll(DocumentTransferDto.class);

        for (String arm : List.of("InlineDocumentTransfer", "DigestOnlyDocumentTransfer")) {
            assertTrue(resolvesProperty(schemas.get(arm), "transferMode", schemas),
                    arm + " does not declare the transferMode discriminator property");
        }
    }

    /**
     * Walks {@code properties}, {@code $ref} and {@code allOf} the way a code generator resolves a schema.
     */
    private static boolean resolvesProperty(Schema<?> schema, String property, Map<String, Schema> allSchemas) {
        if (schema == null) {
            return false;
        }
        if (schema.getProperties() != null && schema.getProperties().containsKey(property)) {
            return true;
        }
        if (schema.get$ref() != null) {
            String name = schema.get$ref().substring(schema.get$ref().lastIndexOf('/') + 1);
            return resolvesProperty(allSchemas.get(name), property, allSchemas);
        }
        if (schema.getAllOf() != null) {
            return schema.getAllOf().stream().anyMatch(member -> resolvesProperty(member, property, allSchemas));
        }
        return false;
    }

    /** Every property a generator would resolve for this schema. */
    private static Set<String> resolvedProperties(Schema<?> schema, Map<String, Schema> allSchemas) {
        if (schema == null) {
            return Set.of();
        }
        Set<String> properties = new HashSet<>();
        if (schema.getProperties() != null) {
            properties.addAll(schema.getProperties().keySet());
        }
        if (schema.get$ref() != null) {
            String name = schema.get$ref().substring(schema.get$ref().lastIndexOf('/') + 1);
            properties.addAll(resolvedProperties(allSchemas.get(name), allSchemas));
        }
        if (schema.getAllOf() != null) {
            schema.getAllOf().forEach(member -> properties.addAll(resolvedProperties(member, allSchemas)));
        }
        return properties;
    }
}
