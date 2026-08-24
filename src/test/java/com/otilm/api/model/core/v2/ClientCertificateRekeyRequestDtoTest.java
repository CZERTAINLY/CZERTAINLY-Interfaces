package com.otilm.api.model.core.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.client.attribute.RequestAttributeV3;
import com.otilm.api.model.common.attribute.common.content.AttributeContentType;
import com.otilm.api.model.common.attribute.v3.content.StringAttributeContentV3;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ClientCertificateRekeyRequestDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void authorizationSecretIsAcceptedOnInputButNeverSerialized() throws Exception {
        ClientCertificateRekeyRequestDto dto = mapper
                .readValue("{\"authorizationSecret\":\"s3cret\"}", ClientCertificateRekeyRequestDto.class);
        assertEquals("s3cret", dto.getAuthorizationSecret());

        String json = mapper.writeValueAsString(dto);
        assertFalse(json.contains("authorizationSecret"),
                "write-only authorizationSecret must never be serialized back to a client");
        assertFalse(json.contains("s3cret"));
    }

    @Test
    void toStringOmitsAuthorizationSecretAndCsr() {
        ClientCertificateRekeyRequestDto dto = new ClientCertificateRekeyRequestDto();
        dto.setAuthorizationSecret("s3cret");
        dto.setRequest("csr-payload-sentinel");
        dto.setAttributes(List.of(sentinelAttribute("attr-value-sentinel")));
        String rendered = dto.toString();
        assertFalse(rendered.contains("s3cret"), "authorizationSecret must not appear in toString");
        assertFalse(rendered.contains("csr-payload-sentinel"),
                "the CSR payload must not appear in toString — the allowlist, not the field exclude, is what hides it");
        assertFalse(rendered.contains("attr-value-sentinel"),
                "rekey attribute values must not appear in toString — the allowlist is what hides them");
    }

    @Test
    void builderToStringOmitsAuthorizationSecretAndCsr() {
        String rendered = ClientCertificateRekeyRequestDto
                .builder()
                .authorizationSecret("s3cret")
                .request("csr-payload-sentinel")
                .attributes(List.of(sentinelAttribute("attr-value-sentinel")))
                .toString();
        assertFalse(rendered.contains("s3cret"),
                "the Lombok-generated builder has its own toString; it must not print the secret");
        assertFalse(rendered.contains("csr-payload-sentinel"),
                "the builder toString must not print the CSR payload either");
        assertFalse(rendered.contains("attr-value-sentinel"),
                "the builder toString must not print rekey attribute values either");
    }

    private static RequestAttributeV3 sentinelAttribute(String value) {
        return new RequestAttributeV3(UUID.randomUUID(), "validityOverride", AttributeContentType.STRING,
                List.of(new StringAttributeContentV3(value)));
    }

    @Test
    void authorizationSecretIsExcludedFromEqualsAndHashCode() {
        ClientCertificateRekeyRequestDto a = new ClientCertificateRekeyRequestDto();
        a.setAuthorizationSecret("secret-a");
        ClientCertificateRekeyRequestDto b = new ClientCertificateRekeyRequestDto();
        b.setAuthorizationSecret("secret-b");
        assertEquals(a, b, "authorizationSecret must not affect equals()");
        assertEquals(a.hashCode(), b.hashCode(), "authorizationSecret must not affect hashCode()");
    }
}
