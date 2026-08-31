package com.otilm.api.model.connector.signatures.contentsigning.common;

import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.common.enums.cryptography.DigestAlgorithm;
import com.otilm.api.model.common.enums.cryptography.SignatureAlgorithm;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.media.Discriminator;
import io.swagger.v3.oas.models.media.Schema;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
                    .of("document", "signerCertificateChain", "signingTime", "signatureAlgorithm",
                            "formattingAttributes")) {
                assertTrue(resolvesProperty(schema, property, schemas),
                        subtype + " does not resolve the inherited property " + property);
            }
        }
    }

    /**
     * Presence is not the part a generated client keys off: the algorithm resolves through a {@code $ref} and across
     * the interface indirection, either of which can drop the requiredMode silently.
     */
    @Test
    void everySubschemaMarksTheSignatureAlgorithmRequired() {
        Map<String, Schema> schemas = ModelConverters.getInstance().readAll(ComputeDtbsInterface.class);

        for (String subtype : List
                .of("PadesComputeDtbsRequestDto", "XadesComputeDtbsRequestDto", "CadesComputeDtbsRequestDto",
                        "JadesComputeDtbsRequestDto")) {
            assertTrue(requiredAnywhere(schemas.get(subtype), "signatureAlgorithm", schemas),
                    subtype + " does not publish signatureAlgorithm as required");
        }
    }

    /**
     * The embed request declares its own {@code signatureAlgorithm} rather than inheriting one, so the assertion above
     * never reaches it and a requiredMode relaxed on that half alone would publish unnoticed.
     */
    @Test
    void theEmbedRequestMarksTheSignatureAlgorithmRequired() {
        Map<String, Schema> schemas = ModelConverters.getInstance().readAll(EmbedSignatureValueRequestDto.class);

        Schema<?> embed = schemas.get("EmbedSignatureValueRequest");
        assertNotNull(embed, "expected a generated schema named EmbedSignatureValueRequest; found " + schemas.keySet());
        assertTrue(requiredAnywhere(embed, "signatureAlgorithm", schemas),
                "EmbedSignatureValueRequest does not publish signatureAlgorithm as required");
    }

    /**
     * The digest an algorithm commits to lives in {@code SignatureAlgorithm}'s Java fields, which the document does not
     * publish, so the component description spells the mapping out. Prose written by hand rots the day the enum gains a
     * member, so the enum is what this asserts against.
     */
    @Test
    void thePublishedEnumNamesTheDigestEachAlgorithmCommitsTo() {
        String description = signatureAlgorithmDescription();

        for (SignatureAlgorithm algorithm : SignatureAlgorithm.values()) {
            DigestAlgorithm committed = committedDigestOrNull(algorithm);
            if (!algorithm.isDigestAlgorithmIsImplicit()) {
                assertNotNull(committed, algorithm.getCode() + " commits to a digest the platform cannot name");
                assertTrue(algorithm.getCode().toUpperCase(Locale.ROOT).contains(committed.getCode().replace("-", "")),
                        algorithm.getCode() + " does not spell its own digest, so the description must map it too");
            } else if (committed != null) {
                assertTrue(description.contains(algorithm.getCode() + " commits to " + committed.getCode()),
                        "the description does not say that " + algorithm.getCode() + " commits to "
                                + committed.getCode());
            } else {
                Pattern clause = Pattern
                        .compile(Pattern.quote(algorithm.getCode() + " commits to ")
                                + "\\S+, which is not a DigestAlgorithm value");
                assertTrue(clause.matcher(description).find(),
                        algorithm.getCode() + " commits to a digest that fills no documentDigestAlgorithm, which the "
                                + "description must say of that algorithm by name");
            }
        }
    }

    /**
     * Every algorithm stays published, including the post-quantum names no connector formats yet: capability is a
     * per-connector matter it refuses with PARAMETER_UNSUPPORTED, not a hole cut in the shared contract.
     */
    @Test
    void thePublishedEnumOffersEveryAlgorithmThePlatformKnows() {
        Schema<?> component = ModelConverters
                .getInstance()
                .readAll(ComputeDtbsInterface.class)
                .get("SignatureAlgorithm");

        assertNotNull(component, "SignatureAlgorithm is not published as a component of the computeDtbs union");
        assertEquals(Arrays.stream(SignatureAlgorithm.values()).map(SignatureAlgorithm::getCode).toList(),
                component.getEnum(), "the published algorithm list is not the enum");
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

    /** Whether {@code property} appears in this schema's own {@code required} list or an inherited one. */
    private static boolean requiredAnywhere(Schema<?> schema, String property, Map<String, Schema> allSchemas) {
        if (schema == null) {
            return false;
        }
        if (schema.getRequired() != null && schema.getRequired().contains(property)) {
            return true;
        }
        if (schema.get$ref() != null) {
            String name = schema.get$ref().substring(schema.get$ref().lastIndexOf('/') + 1);
            return requiredAnywhere(allSchemas.get(name), property, allSchemas);
        }
        if (schema.getAllOf() != null) {
            return schema.getAllOf().stream().anyMatch(member -> requiredAnywhere(member, property, allSchemas));
        }
        return false;
    }

    /** The description the document actually publishes for the algorithm. */
    private static String signatureAlgorithmDescription() {
        Schema<?> component = ModelConverters
                .getInstance()
                .readAll(ComputeDtbsInterface.class)
                .get("SignatureAlgorithm");
        if (component == null || component.getDescription() == null) {
            return fail("the SignatureAlgorithm component publishes no description");
        }
        return component.getDescription();
    }

    /** The digest algorithm an algorithm's signatures carry, or {@code null} when the platform can name none. */
    private static DigestAlgorithm committedDigestOrNull(SignatureAlgorithm algorithm) {
        try {
            return DigestAlgorithm.findByOid(algorithm.getDigestAlgorithmIdentifier().getAlgorithm().getId());
        } catch (ValidationException e) {
            return null;
        }
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
