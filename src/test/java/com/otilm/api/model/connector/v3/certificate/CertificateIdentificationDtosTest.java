package com.otilm.api.model.connector.v3.certificate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.client.attribute.RequestAttributeV3;
import com.otilm.api.model.common.attribute.common.content.AttributeContentType;
import com.otilm.api.model.common.attribute.v3.content.StringAttributeContentV3;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CertificateIdentificationDtosTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void requestRoundTrips() throws Exception {
        CertificateIdentificationRequestDtoV3 dto = new CertificateIdentificationRequestDtoV3();
        dto.setAuthorityAttributes(List.of());
        dto.setRaProfileAttributes(List.of());
        dto.setCertificate("MIIBkjCCATs...");
        dto
                .setAttributes(List
                        .of(new RequestAttributeV3(UUID.fromString("1b7f2c3a-0000-4000-8000-000000000001"), "caProfile",
                                AttributeContentType.STRING, List.of(new StringAttributeContentV3("profile-a")))));
        String json = mapper.writeValueAsString(dto);
        CertificateIdentificationRequestDtoV3 back = mapper
                .readValue(json, CertificateIdentificationRequestDtoV3.class);
        assertEquals("MIIBkjCCATs...", back.getCertificate());
        // The polymorphic RequestAttribute binding must resolve the v3 subtype and keep name + value.
        RequestAttributeV3 attribute = (RequestAttributeV3) back.getAttributes().get(0);
        assertEquals("caProfile", attribute.getName());
        assertEquals("profile-a", ((StringAttributeContentV3) attribute.getContent().get(0)).getData());
    }

    @Test
    void requestToStringOmitsAttributeValues() {
        CertificateIdentificationRequestDtoV3 dto = new CertificateIdentificationRequestDtoV3();
        dto
                .setAttributes(List
                        .of(new RequestAttributeV3(UUID.randomUUID(), "caProfile", AttributeContentType.STRING,
                                List.of(new StringAttributeContentV3("sentinel-value")))));

        assertFalse(dto.toString().contains("sentinel-value"),
                "identify attribute values must not appear in toString (@ToString.Exclude)");
    }

    @Test
    void responseRoundTrips() throws Exception {
        CertificateIdentificationResponseDto dto = new CertificateIdentificationResponseDto();
        dto.setMeta(List.of());
        String json = mapper.writeValueAsString(dto);
        CertificateIdentificationResponseDto back = mapper.readValue(json, CertificateIdentificationResponseDto.class);
        assertEquals(0, back.getMeta().size());
    }
}
