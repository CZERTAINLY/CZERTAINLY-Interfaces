package com.otilm.api.model.client.notification;

import com.otilm.api.model.core.notification.NotificationDataCategory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotificationProfileUpdateRequestDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void absentCategoriesDeserializeAsNull() throws Exception {
        NotificationProfileUpdateRequestDto dto = mapper.readValue("{}", NotificationProfileUpdateRequestDto.class);
        assertNull(dto.getEventDataCategories());
    }

    @Test
    void emptyCategoriesDeserializeAsEmptyList() throws Exception {
        NotificationProfileUpdateRequestDto dto = mapper.readValue(
                "{\"eventDataCategories\":[]}", NotificationProfileUpdateRequestDto.class);
        assertNotNull(dto.getEventDataCategories());
        assertTrue(dto.getEventDataCategories().isEmpty());
    }

    @Test
    void categoriesRideWireCodes() throws Exception {
        NotificationProfileUpdateRequestDto dto = new NotificationProfileUpdateRequestDto();
        dto.setEventDataCategories(List.of(
                NotificationDataCategory.CUSTOM_ATTRIBUTES, NotificationDataCategory.OBJECT_CONTENT));
        String json = mapper.writeValueAsString(dto);
        assertTrue(json.contains("\"eventDataCategories\":[\"customAttributes\",\"objectContent\"]"), json);
    }

    @Test
    void createRequestInheritsCategories() throws Exception {
        NotificationProfileRequestDto dto = mapper.readValue(
                "{\"name\":\"profile\",\"eventDataCategories\":[\"metadata\"]}", NotificationProfileRequestDto.class);
        assertNotNull(dto.getEventDataCategories());
        assertTrue(dto.getEventDataCategories().contains(NotificationDataCategory.METADATA));
    }
}
