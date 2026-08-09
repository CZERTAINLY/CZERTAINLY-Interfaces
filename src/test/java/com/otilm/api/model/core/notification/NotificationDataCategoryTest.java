package com.otilm.api.model.core.notification;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NotificationDataCategoryTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void serializesToJsonValueCode() throws Exception {
        assertEquals("\"customAttributes\"", mapper.writeValueAsString(NotificationDataCategory.CUSTOM_ATTRIBUTES));
        assertEquals("\"metadata\"", mapper.writeValueAsString(NotificationDataCategory.METADATA));
        assertEquals("\"associations\"", mapper.writeValueAsString(NotificationDataCategory.ASSOCIATIONS));
        assertEquals("\"objectContent\"", mapper.writeValueAsString(NotificationDataCategory.OBJECT_CONTENT));
    }

    @Test
    void deserializesFromJsonValueCode() throws Exception {
        assertEquals(NotificationDataCategory.CUSTOM_ATTRIBUTES,
                mapper.readValue("\"customAttributes\"", NotificationDataCategory.class));
        assertEquals(NotificationDataCategory.METADATA,
                mapper.readValue("\"metadata\"", NotificationDataCategory.class));
        assertEquals(NotificationDataCategory.ASSOCIATIONS,
                mapper.readValue("\"associations\"", NotificationDataCategory.class));
        assertEquals(NotificationDataCategory.OBJECT_CONTENT,
                mapper.readValue("\"objectContent\"", NotificationDataCategory.class));
    }

    @Test
    void unknownCodeThrowsValidationException() {
        // Deserializing an unknown code surfaces a Jackson mapping error whose root
        // cause carries the validation failure raised by the enum factory method.
        JsonMappingException ex = assertThrows(JsonMappingException.class,
                () -> mapper.readValue("\"bogus\"", NotificationDataCategory.class));
        assertInstanceOf(ValidationException.class, ex.getCause(),
                "expected the underlying cause to be ValidationException, got: " + ex.getCause());
    }

    @Test
    void findByCodeRejectsUnknownDirectly() {
        assertThrows(ValidationException.class, () -> NotificationDataCategory.findByCode("bogus"));
    }

    @Test
    void labelsAndDescriptionsArePopulated() {
        for (NotificationDataCategory category : NotificationDataCategory.values()) {
            assertFalse(category.getLabel().isBlank(), "label missing for " + category.name());
            assertFalse(category.getDescription().isBlank(), "description missing for " + category.name());
        }
    }
}
