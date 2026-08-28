package com.otilm.api.model.connector.v3.certificate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.core.certificate.CertificateKeyUsage;
import com.otilm.api.model.core.certificate.CertificateType;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class X509RequestContentTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void keyUsageSerializesAsAListOfCodes() {
        X509RequestContent content = new X509RequestContent();
        content.setCertificateType(CertificateType.X509);
        content.setKeyUsage(List.of(CertificateKeyUsage.DIGITAL_SIGNATURE, CertificateKeyUsage.KEY_ENCIPHERMENT));

        JsonNode json = mapper.valueToTree(content);

        assertEquals("digitalSignature", json.get("keyUsage").get(0).asText());
        assertEquals("keyEncipherment", json.get("keyUsage").get(1).asText());
    }

    @Test
    void extendedKeyUsageRoundTrips() throws Exception {
        X509RequestContent original = new X509RequestContent();
        original.setCertificateType(CertificateType.X509);
        original.setExtendedKeyUsage(List.of("1.3.6.1.5.5.7.3.1"));

        X509RequestContent result = mapper.readValue(mapper.writeValueAsString(original), X509RequestContent.class);

        assertEquals(List.of("1.3.6.1.5.5.7.3.1"), result.getExtendedKeyUsage());
    }

    @Test
    void unsetTypedListsAreOmittedFromJson() throws Exception {
        X509RequestContent content = new X509RequestContent();
        content.setCertificateType(CertificateType.X509);
        content.setSubject(List.of(new RdnEntry("CN", "host.example.com")));

        String json = mapper.writeValueAsString(content);

        assertFalse(json.contains("keyUsage"));
        assertFalse(json.contains("extendedKeyUsage"));
    }

    @Test
    void contentCarryingOnlyKeyUsageCountsAsProvided() {
        // Without this, a request whose only mapped target is Key Usage is rejected as empty content.
        X509RequestContent content = new X509RequestContent();
        content.setKeyUsage(List.of(CertificateKeyUsage.DIGITAL_SIGNATURE));

        assertTrue(content.isRequestContentProvided());
    }

    @Test
    void contentCarryingOnlyExtendedKeyUsageCountsAsProvided() {
        X509RequestContent content = new X509RequestContent();
        content.setExtendedKeyUsage(List.of("1.3.6.1.5.5.7.3.1"));

        assertTrue(content.isRequestContentProvided());
    }

    @Test
    void entirelyEmptyContentIsNotProvided() {
        assertFalse(new X509RequestContent().isRequestContentProvided());
    }
}
