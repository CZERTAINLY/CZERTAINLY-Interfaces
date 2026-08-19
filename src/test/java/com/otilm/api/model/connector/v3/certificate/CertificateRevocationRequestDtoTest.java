package com.otilm.api.model.connector.v3.certificate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.client.attribute.RequestAttributeV3;
import com.otilm.api.model.common.attribute.common.content.AttributeContentType;
import com.otilm.api.model.common.attribute.v3.content.StringAttributeContentV3;
import com.otilm.api.model.core.authority.CertificateRevocationReason;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CertificateRevocationRequestDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void roundTripsAllFields() throws Exception {
        CertificateRevocationRequestDtoV3 dto = new CertificateRevocationRequestDtoV3();
        dto.setAuthorityAttributes(List.of());
        dto.setRaProfileAttributes(List.of());
        dto.setCertificate("MIIBkjCCATs...");
        dto.setReason(CertificateRevocationReason.KEY_COMPROMISE);
        dto.setAttributes(List.of());
        dto.setMeta(List.of());

        String json = mapper.writeValueAsString(dto);
        CertificateRevocationRequestDtoV3 back = mapper.readValue(json, CertificateRevocationRequestDtoV3.class);
        assertEquals("MIIBkjCCATs...", back.getCertificate());
        assertEquals(CertificateRevocationReason.KEY_COMPROMISE, back.getReason());
    }

    @Test
    void toStringOmitsAttributeValues() {
        CertificateRevocationRequestDtoV3 dto = new CertificateRevocationRequestDtoV3();
        dto
                .setAttributes(List
                        .of(new RequestAttributeV3(UUID.randomUUID(), "caProfile", AttributeContentType.STRING,
                                List.of(new StringAttributeContentV3("sentinel-value")))));

        assertFalse(dto.toString().contains("sentinel-value"),
                "revoke attribute values must not appear in toString (@ToString.Exclude)");
    }
}
