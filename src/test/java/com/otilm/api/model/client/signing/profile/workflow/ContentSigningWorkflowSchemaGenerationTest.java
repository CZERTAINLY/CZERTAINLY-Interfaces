package com.otilm.api.model.client.signing.profile.workflow;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.media.Schema;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Asserts that a stock generated client sees the profile's signature-parameter fields as the shared types they are
 * declared as, rather than as opaque objects.
 */
class ContentSigningWorkflowSchemaGenerationTest {

    private static final String REF = "#/components/schemas/";

    @Test
    void theRequestPublishesTheDefaultsAsTheParametersUnion() {
        Map<String, Schema> properties = generate(ContentSigningWorkflowRequestDto.class);

        assertEquals(REF + "SignatureParameters", properties.get("defaultSignatureParameters").get$ref());
    }

    @Test
    void theResponsePublishesTheDefaultsAsTheParametersUnion() {
        Map<String, Schema> properties = generate(ContentSigningWorkflowDto.class);

        assertEquals(REF + "SignatureParameters", properties.get("defaultSignatureParameters").get$ref());
    }

    /** The declared type is a Set, so the generated client must not accept a group twice. */
    @Test
    void theAllowListPublishesAsAUniqueCollectionOfGroups() {
        Schema<?> allowList = generate(ContentSigningWorkflowRequestDto.class).get("allowedRequestParameterGroups");

        assertEquals("array", allowList.getType());
        assertEquals(Boolean.TRUE, allowList.getUniqueItems());
        assertNotNull(allowList.getItems(), "the allow-list publishes no item schema");
        assertEquals(REF + "SignatureParameterGroup", allowList.getItems().get$ref());
    }

    private static Map<String, Schema> generate(Class<?> type) {
        Schema<?> schema = ModelConverters
                .getInstance()
                .readAll(type)
                .get(type.getAnnotation(io.swagger.v3.oas.annotations.media.Schema.class).name());
        assertNotNull(schema, "expected a generated schema for " + type.getSimpleName());
        return schema.getProperties();
    }
}
