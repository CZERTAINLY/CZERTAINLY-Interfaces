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
import static org.junit.jupiter.api.Assertions.assertTrue;

class CertificateRenewRequestDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void roundTripsAllFields() throws Exception {
        CertificateRenewRequestDtoV3 dto = new CertificateRenewRequestDtoV3();
        dto.setAuthorityAttributes(List.of());
        dto.setRaProfileAttributes(List.of());
        dto.setRequest("MIICij...");
        dto.setFormat(CertificateRequestFormat.PKCS10);
        dto.setExistingCertificate("MIIBkjCCATs...");
        dto.setReuseKey(false);
        dto.setAttributes(List.of());
        dto.setMeta(List.of());

        String json = mapper.writeValueAsString(dto);
        CertificateRenewRequestDtoV3 back = mapper.readValue(json, CertificateRenewRequestDtoV3.class);
        assertEquals("MIICij...", back.getRequest());
        assertEquals("MIIBkjCCATs...", back.getExistingCertificate());
        assertEquals(false, back.isReuseKey());
    }

    @Test
    void toStringOmitsAttributeValues() {
        CertificateRenewRequestDtoV3 dto = new CertificateRenewRequestDtoV3();
        dto
                .setAttributes(List
                        .of(new RequestAttributeV3(UUID.randomUUID(), "validityOverride", AttributeContentType.STRING,
                                List.of(new StringAttributeContentV3("sentinel-value")))));

        assertFalse(dto.toString().contains("sentinel-value"),
                "renew attribute values must not appear in toString (@ToString.Exclude)");
    }

    @Test
    void reuseKeyDefaultsFalse() throws Exception {
        String json = "{\"authorityAttributes\":[],\"raProfileAttributes\":[],\"existingCertificate\":\"X\"}";
        CertificateRenewRequestDtoV3 back = mapper.readValue(json, CertificateRenewRequestDtoV3.class);
        assertTrue(!back.isReuseKey());
    }

    @Test
    void noRequestAndNoReuseKey_failsConstraint() {
        CertificateRenewRequestDtoV3 dto = new CertificateRenewRequestDtoV3();
        dto.setReuseKey(false);
        assertFalse(dto.isRequestProvidedOrKeyReused(),
                "Renew with neither a CSR nor reuseKey=true must fail the constraint");
    }

    @Test
    void blankRequestAndNoReuseKey_failsConstraint() {
        CertificateRenewRequestDtoV3 dto = new CertificateRenewRequestDtoV3();
        dto.setReuseKey(false);
        dto.setRequest("   \t\n");
        assertFalse(dto.isRequestProvidedOrKeyReused(),
                "A whitespace-only CSR must not satisfy the constraint when reuseKey=false");
    }

    @Test
    void requestProvided_passes() {
        CertificateRenewRequestDtoV3 dto = new CertificateRenewRequestDtoV3();
        dto.setReuseKey(false);
        dto.setRequest("MIICij...");
        assertTrue(dto.isRequestProvidedOrKeyReused(),
                "A non-blank CSR satisfies the constraint regardless of reuseKey");
    }

    @Test
    void reuseKeyWithoutRequest_passes() {
        CertificateRenewRequestDtoV3 dto = new CertificateRenewRequestDtoV3();
        dto.setReuseKey(true);
        assertTrue(dto.isRequestProvidedOrKeyReused(),
                "reuseKey=true delegates proof-of-possession to the CA, so a CSR is optional");
    }
}
