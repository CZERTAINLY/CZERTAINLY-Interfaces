package com.otilm.api.model.connector.notification;

import com.otilm.api.model.common.NameAndUuidDto;
import com.otilm.api.model.common.attribute.common.content.AttributeContentType;
import com.otilm.api.model.core.auth.Resource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotificationEventObjectDataDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void roundTripsAllCategories() throws Exception {
        NotificationAttributeDto attribute = new NotificationAttributeDto();
        attribute.setName("department");
        attribute.setLabel("Department");
        attribute.setContentType(AttributeContentType.STRING);
        attribute.setValues(List.of("E-Commerce"));

        NotificationAttributeDto meta = new NotificationAttributeDto();
        meta.setName("discoverySource");
        meta.setLabel("Discovery Source");
        meta.setContentType(AttributeContentType.STRING);
        meta.setValues(List.of("10.20.30.0/24"));
        meta.setSourceObjects(List.of(new NameAndUuidDto("3c1e88f0-0000-0000-0000-000000000001", "weekly-dc1-sweep")));

        NotificationMetadataGroupDto group = new NotificationMetadataGroupDto();
        group.setConnectorName("Network-Discovery");
        group.setSourceObjectType(Resource.DISCOVERY);
        group.setAttributes(Map.of("discoverySource", meta));

        NotificationAssociationDto subject = new NotificationAssociationDto();
        subject.setResource(Resource.CERTIFICATE);
        subject.setUuid("e1f6a7c2-0000-0000-0000-000000000002");
        subject.setName("shop.acme.example");

        NotificationObjectContentDto content = new NotificationObjectContentDto();
        content.setFormat("X509_DER_BASE64");
        content.setData("MIIBmTCCAT+gAwIBAgIUfake");

        NotificationEventObjectDataDto dto = new NotificationEventObjectDataDto();
        dto.setSubject(subject);
        dto.setCustomAttributes(Map.of("department", attribute));
        dto.setMetadata(List.of(group));
        dto.setAssociations(List.of(subject));
        dto.setContent(content);

        String json = mapper.writeValueAsString(dto);
        NotificationEventObjectDataDto back = mapper.readValue(json, NotificationEventObjectDataDto.class);

        assertEquals(dto, back);
        // Enum fields ride their @JsonValue codes on the wire.
        assertTrue(json.contains("\"resource\":\"certificates\""), json);
        assertTrue(json.contains("\"contentType\":\"string\""), json);
        assertTrue(json.contains("\"sourceObjectType\":\"discoveries\""), json);
    }

    @Test
    void emptyDtoRoundTripsWithoutFailure() throws Exception {
        String json = mapper.writeValueAsString(new NotificationEventObjectDataDto());
        assertNotNull(mapper.readValue(json, NotificationEventObjectDataDto.class));
    }

    @Test
    void nonStringScalarValuesKeepTheirWireTypes() throws Exception {
        NotificationAttributeDto attribute = new NotificationAttributeDto();
        attribute.setName("port");
        attribute.setLabel("Port");
        attribute.setContentType(AttributeContentType.INTEGER);
        attribute.setValues(List.of(443, true, 1.5));

        String json = mapper.writeValueAsString(attribute);
        assertTrue(json.contains("\"values\":[443,true,1.5]"), json);

        NotificationAttributeDto back = mapper.readValue(json, NotificationAttributeDto.class);
        // Untyped scalars land as their natural JSON Java types -- the contract template
        // authors and providers can rely on across artifact versions.
        assertEquals(Integer.valueOf(443), back.getValues().get(0));
        assertEquals(Boolean.TRUE, back.getValues().get(1));
        assertEquals(Double.valueOf(1.5), back.getValues().get(2));
    }

    @Test
    void associationToStringIncludesInheritedIdentity() {
        NotificationAssociationDto association = new NotificationAssociationDto();
        association.setResource(Resource.GROUP);
        association.setUuid("0aa13f77-0000-0000-0000-000000000003");
        association.setName("web-team");

        String printed = association.toString();
        assertTrue(printed.contains("web-team"), printed);
        assertTrue(printed.contains("0aa13f77"), printed);
    }
}
