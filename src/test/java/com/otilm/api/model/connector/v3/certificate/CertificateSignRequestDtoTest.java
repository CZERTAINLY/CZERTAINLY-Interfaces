package com.otilm.api.model.connector.v3.certificate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.client.attribute.RequestAttributeV3;
import com.otilm.api.model.common.attribute.common.content.AttributeContentType;
import com.otilm.api.model.common.attribute.v3.content.StringAttributeContentV3;
import com.otilm.api.model.core.enums.CertificateRequestFormat;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class CertificateSignRequestDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void roundTripsAllFields() throws Exception {
        CertificateSignRequestDtoV3 dto = new CertificateSignRequestDtoV3();
        dto.setAuthorityAttributes(List.of());
        dto.setRaProfileAttributes(List.of());
        dto.setRequest("MIICij...");
        dto.setFormat(CertificateRequestFormat.PKCS10);
        dto.setAttributes(List.of());
        dto.setMeta(List.of());

        String json = mapper.writeValueAsString(dto);
        CertificateSignRequestDtoV3 back = mapper.readValue(json, CertificateSignRequestDtoV3.class);
        assertEquals("MIICij...", back.getRequest());
        assertEquals(CertificateRequestFormat.PKCS10, back.getFormat());
    }

    @Test
    void metaOptional() throws Exception {
        String json = "{\"authorityAttributes\":[],\"raProfileAttributes\":[],\"request\":\"X\"}";
        CertificateSignRequestDtoV3 back = mapper.readValue(json, CertificateSignRequestDtoV3.class);
        assertNull(back.getMeta());
    }

    @Test
    void toStringOmitsAttributeValues() {
        CertificateSignRequestDtoV3 dto = new CertificateSignRequestDtoV3();
        dto
                .setAttributes(List
                        .of(new RequestAttributeV3(UUID.randomUUID(), "caProfile", AttributeContentType.STRING,
                                List.of(new StringAttributeContentV3("sentinel-value")))));

        assertFalse(dto.toString().contains("sentinel-value"),
                "issue attribute values must not appear in toString (@ToString.Exclude)");
    }
}
