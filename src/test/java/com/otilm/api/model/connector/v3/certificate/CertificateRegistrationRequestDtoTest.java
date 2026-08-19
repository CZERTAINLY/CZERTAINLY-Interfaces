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

class CertificateRegistrationRequestDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void roundTripsAllFields() throws Exception {
        CertificateExtension ext = new CertificateExtension();
        ext.setOid("2.5.29.37");
        ext.setCritical(false);
        ext.setValueBase64("MA0GCysGAQQBgjcVAQUDAg==");

        CertificateRegistrationRequestDtoV3 dto = new CertificateRegistrationRequestDtoV3();
        dto.setAuthorityAttributes(List.of());
        dto.setRaProfileAttributes(List.of());
        dto.setSubjectDn("CN=device-7,O=Acme");
        dto.setSubjectAltName("DNS:device-7.acme.local");
        dto.setExtensions(List.of(ext));
        dto.setAttributes(List.of());

        String json = mapper.writeValueAsString(dto);
        CertificateRegistrationRequestDtoV3 back = mapper.readValue(json, CertificateRegistrationRequestDtoV3.class);
        assertEquals("CN=device-7,O=Acme", back.getSubjectDn());
        assertEquals("DNS:device-7.acme.local", back.getSubjectAltName());
        assertEquals(1, back.getExtensions().size());
        assertEquals("2.5.29.37", back.getExtensions().get(0).getOid());
    }

    @Test
    void toStringOmitsAttributeValues() {
        CertificateRegistrationRequestDtoV3 dto = new CertificateRegistrationRequestDtoV3();
        dto
                .setAttributes(List
                        .of(new RequestAttributeV3(UUID.randomUUID(), "caProfile", AttributeContentType.STRING,
                                List.of(new StringAttributeContentV3("sentinel-value")))));

        assertFalse(dto.toString().contains("sentinel-value"),
                "register attribute values must not appear in toString (@ToString.Exclude)");
    }
}
