package com.otilm.api.model.connector.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class NotificationProviderNotifyRequestDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void payloadFieldsAreExcludedFromToString() {
        NotificationProviderNotifyRequestDto request = new NotificationProviderNotifyRequestDto();
        NotificationEventObjectDataDto objectData = new NotificationEventObjectDataDto();
        NotificationAssociationDto subject = new NotificationAssociationDto();
        subject.setName("marker-subject-name");
        objectData.setSubject(subject);
        request.setObjectData(objectData);
        request.setNotificationData(java.util.Map.of("credential", "marker-credential-value"));

        String printed = request.toString();
        assertFalse(printed.contains("marker-subject-name"), printed);
        assertFalse(printed.contains("objectData"), printed);
        assertFalse(printed.contains("marker-credential-value"), printed);
        assertFalse(printed.contains("notificationData"), printed);
    }

    @Test
    void deserializationToleratesAbsentObjectData() throws Exception {
        NotificationProviderNotifyRequestDto request = mapper.readValue(
                "{\"recipients\":[],\"eventType\":\"other\"}", NotificationProviderNotifyRequestDto.class);
        assertNull(request.getObjectData());
    }

    @Test
    void objectDataRoundTrips() throws Exception {
        NotificationProviderNotifyRequestDto request = new NotificationProviderNotifyRequestDto();
        NotificationEventObjectDataDto objectData = new NotificationEventObjectDataDto();
        NotificationAssociationDto subject = new NotificationAssociationDto();
        subject.setUuid("e1f6a7c2-0000-0000-0000-000000000002");
        objectData.setSubject(subject);
        request.setObjectData(objectData);

        NotificationProviderNotifyRequestDto back = mapper.readValue(
                mapper.writeValueAsString(request), NotificationProviderNotifyRequestDto.class);
        assertEquals(request.getObjectData(), back.getObjectData());
    }
}
