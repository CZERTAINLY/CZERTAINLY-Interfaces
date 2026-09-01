package com.otilm.api.interfaces.core.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.core.cbom.CbomDto;
import com.otilm.api.model.core.certificate.CertificateDto;
import com.otilm.api.model.core.cryptography.key.KeyItemDto;
import com.otilm.api.model.core.search.AttributeProjectable;
import com.otilm.api.model.core.search.ConfigurableColumnsDocs;
import com.otilm.api.model.core.secret.SecretDto;
import com.otilm.api.model.core.signing.signingrecord.SigningRecordListDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

import static com.otilm.api.testsupport.OpenApiProseAssertions.assertNoJargon;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Guards the published prose of the configurable-column contract.
 *
 * <p>
 * Ordering, column selection and the two catalogue flags are one contract spread over every configurable-column
 * listing, its field catalogue and its listing object. A caller reads only the generated document, so a listing that
 * quietly loses the wording, or gains {@code sort} and {@code columns} without it, is a contract that is documented
 * everywhere but there. The lists below are deliberately explicit: adding a resource to the contract means adding it
 * here too.
 */
class ConfigurableColumnsDocTest {

    /** The listings whose request body carries {@code sort} and {@code columns}, by controller and method name. */
    private static final Map<Class<?>, String> LISTINGS = Map
            .of(CertificateController.class, "listCertificates", CryptographicKeyController.class,
                    "listCryptographicKeys", DiscoveryController.class, "listDiscoveries",
                    SecretManagementController.class, "listSecrets", CbomController.class, "listCboms",
                    SigningRecordController.class, "listSigningRecords",
                    com.otilm.api.interfaces.core.web.v2.ConnectorController.class, "listConnectors");

    /** The field catalogues that feed those listings. All seven declare the operation under the same method name. */
    private static final List<Class<?>> CATALOGUES = List
            .of(CertificateController.class, CryptographicKeyController.class, DiscoveryController.class,
                    SecretManagementController.class, CbomController.class, SigningRecordController.class,
                    com.otilm.api.interfaces.core.web.v2.ConnectorController.class);

    /** The listing objects that carry the projected attribute values. */
    private static final List<Class<?>> PROJECTION_CARRIERS = List
            .of(CertificateDto.class, KeyItemDto.class, com.otilm.api.model.core.connector.v2.ConnectorDto.class,
                    SecretDto.class, CbomDto.class, com.otilm.api.model.client.discovery.DiscoveryListDto.class,
                    SigningRecordListDto.class);

    /**
     * The carriers whose resource the platform registers no custom, metadata or data attributes against. They implement
     * the projection like the rest - the contract is one shape for all seven - but showing the shared example on them
     * would document a payload the listing cannot produce, so they carry the description alone.
     */
    private static final List<Class<?>> CARRIERS_WITHOUT_ATTRIBUTE_SOURCES = List
            .of(CbomDto.class, SigningRecordListDto.class);

    /** The form the field catalogue publishes an attribute-sourced identifier under. */
    private static final Pattern ATTRIBUTE_IDENTIFIER = Pattern.compile("^[^|]+\\|[A-Z]+$");

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void everyListingDocumentsOrderingAndColumns() {
        LISTINGS.forEach((controller, methodName) -> {
            Operation op = operation(controller, methodName);
            assertTrue(op.description().contains(ConfigurableColumnsDocs.SORT_AND_COLUMNS),
                    methodName + " does not carry the shared ordering and columns wording");
            assertTrue(op.description().contains(ConfigurableColumnsDocs.ATTRIBUTE_PROJECTION),
                    methodName + " does not carry the shared attribute projection wording");
            assertNoJargon(methodName, op.description());
        });
    }

    @Test
    void everyListingCarriesAWorkedRequestExample() {
        LISTINGS.forEach((controller, methodName) -> {
            ExampleObject[] examples = requestBodyExamples(controller, methodName);
            assertTrue(examples.length > 0, methodName + " declares no request example");
            for (ExampleObject example : examples) {
                JsonNode body = assertDoesNotThrow(() -> MAPPER.readTree(example.value()),
                        methodName + " request example is not valid JSON, so it reaches the document as a string");
                assertTrue(body.has("sort"), methodName + " request example does not show sort");
                assertTrue(body.has("columns"), methodName + " request example does not show columns");
                for (JsonNode column : body.get("columns")) {
                    assertTrue(column.has("fieldSource") && column.has("fieldIdentifier"),
                            methodName + " request example addresses a column without both halves of its address");
                    assertIdentifierMatchesItsSource(methodName, column.get("fieldSource").asText(),
                            column.get("fieldIdentifier").asText());
                }
            }
        });
    }

    @Test
    void everyFieldCatalogueDocumentsTheCapabilityFlags() {
        for (Class<?> controller : CATALOGUES) {
            Operation op = operation(controller, "getSearchableFieldInformation");
            assertTrue(op.description().contains(ConfigurableColumnsDocs.CATALOGUE_FLAGS),
                    controller.getSimpleName() + " does not document displayable and sortable");
            assertNoJargon(controller.getSimpleName(), op.description());
        }
    }

    @Test
    void everyProjectionCarrierDescribesTheProjectedValuesTheSameWay() {
        for (Class<?> carrier : PROJECTION_CARRIERS) {
            assertTrue(AttributeProjectable.class.isAssignableFrom(carrier),
                    carrier.getSimpleName() + " does not implement AttributeProjectable");
            Field field = assertDoesNotThrow(() -> carrier.getDeclaredField("attributeValues"),
                    carrier.getSimpleName() + " has no attributeValues field");
            Schema schema = field.getAnnotation(Schema.class);
            assertNotNull(schema, carrier.getSimpleName() + " does not document attributeValues");
            assertEquals(AttributeProjectable.ATTRIBUTE_VALUES_DESCRIPTION, schema.description(),
                    carrier.getSimpleName() + " describes attributeValues in its own words");
            if (CARRIERS_WITHOUT_ATTRIBUTE_SOURCES.contains(carrier)) {
                assertEquals("", schema.example(),
                        carrier.getSimpleName() + " shows an attribute example for a resource that has no attributes");
            } else {
                assertEquals(AttributeProjectable.ATTRIBUTE_VALUES_EXAMPLE, schema.example(),
                        carrier.getSimpleName() + " does not show the shared attributeValues example");
            }
        }
    }

    @Test
    void theSharedAttributeValuesExampleIsValidJsonNestedBySourceThenIdentifier() {
        JsonNode example = assertDoesNotThrow(() -> MAPPER.readTree(AttributeProjectable.ATTRIBUTE_VALUES_EXAMPLE),
                "the attributeValues example is not valid JSON, so it reaches the document as a string");
        assertTrue(example.fieldNames().hasNext(), "the attributeValues example shows no field source");
        Map.Entry<String, JsonNode> source = example.fields().next();
        JsonNode bySource = source.getValue();
        assertTrue(bySource.fieldNames().hasNext(), "the attributeValues example shows no field identifier");
        assertTrue(bySource.fields().next().getValue().isArray(),
                "the attributeValues example does not show values as a list");
        bySource
                .fieldNames()
                .forEachRemaining(identifier -> assertIdentifierMatchesItsSource("the attributeValues example",
                        source.getKey(), identifier));
    }

    /**
     * An attribute-sourced field is published under {@code name|CONTENT_TYPE}, because a name alone is ambiguous when
     * one attribute name is registered against two content types - and a column addressed by the bare name therefore
     * matches nothing. A property field is published under its own identifier and carries no suffix.
     */
    private static void assertIdentifierMatchesItsSource(String context, String fieldSource, String fieldIdentifier) {
        if ("property".equals(fieldSource)) {
            assertFalse(fieldIdentifier.contains("|"),
                    context + " suffixes the property identifier " + fieldIdentifier + " with a content type");
            return;
        }
        assertTrue(ATTRIBUTE_IDENTIFIER.matcher(fieldIdentifier).matches(), context + " addresses " + fieldSource
                + " field " + fieldIdentifier + " without the name|CONTENT_TYPE form the catalogue publishes");
    }

    @Test
    void theUnpagedConnectorListingPointsAtTheOneThatTakesColumns() {
        Operation op = operation(ConnectorController.class, "listConnectors");
        assertTrue(op.description().contains("POST /v2/connectors/list"),
                "the v1 Connector listing does not say where filters, ordering and columns live");
        assertNoJargon("v1 listConnectors", op.description());
    }

    private static Operation operation(Class<?> controller, String methodName) {
        Method method = Arrays
                .stream(controller.getDeclaredMethods())
                .filter(m -> m.getName().equals(methodName))
                .findFirst()
                .orElseThrow(() -> new AssertionError(controller.getSimpleName() + " has no " + methodName));
        Operation op = method.getAnnotation(Operation.class);
        assertNotNull(op, "missing @Operation on " + controller.getSimpleName() + "." + methodName);
        return op;
    }

    private static ExampleObject[] requestBodyExamples(Class<?> controller, String methodName) {
        Method method = Arrays
                .stream(controller.getDeclaredMethods())
                .filter(m -> m.getName().equals(methodName))
                .findFirst()
                .orElseThrow(() -> new AssertionError(controller.getSimpleName() + " has no " + methodName));
        return Arrays
                .stream(method.getParameters())
                .map(p -> p.getAnnotation(io.swagger.v3.oas.annotations.parameters.RequestBody.class))
                .filter(Objects::nonNull)
                .flatMap(rb -> Arrays.stream(rb.content()))
                .flatMap(content -> Arrays.stream(content.examples()))
                .toArray(ExampleObject[]::new);
    }
}
