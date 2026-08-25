package com.otilm.api.model.common.attribute.common.constraint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class JsonSchemaAttributeConstraintTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void serializesDiscriminatorAndData() {
        JsonSchemaAttributeConstraint constraint = new JsonSchemaAttributeConstraint("desc", "err",
                "{\"type\":\"object\"}");

        JsonNode json = mapper.valueToTree(constraint);

        assertEquals("jsonSchema", json.get("type").asText());
        assertEquals("{\"type\":\"object\"}", json.get("data").asText());
        assertEquals("err", json.get("errorMessage").asText());
    }

    @Test
    void deserializesViaBaseClass() throws Exception {
        String json = """
                {
                  "type": "jsonSchema",
                  "description": "shape",
                  "errorMessage": "wrong shape",
                  "data": "{\\"type\\":\\"object\\"}"
                }
                """;

        BaseAttributeConstraint<?> base = mapper.readValue(json, BaseAttributeConstraint.class);

        assertInstanceOf(JsonSchemaAttributeConstraint.class, base);
        assertEquals(AttributeConstraintType.JSON_SCHEMA, base.getType());
        assertEquals("{\"type\":\"object\"}", base.getData());
    }

    @Test
    void codeResolvesThroughTheEnum() {
        assertEquals(AttributeConstraintType.JSON_SCHEMA, AttributeConstraintType.fromCode("jsonSchema"));
    }
}
