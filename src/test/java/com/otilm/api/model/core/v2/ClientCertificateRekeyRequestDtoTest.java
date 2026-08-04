package com.otilm.api.model.core.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ClientCertificateRekeyRequestDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void authorizationSecretIsAcceptedOnInputButNeverSerialized() throws Exception {
        ClientCertificateRekeyRequestDto dto =
                mapper.readValue("{\"authorizationSecret\":\"s3cret\"}", ClientCertificateRekeyRequestDto.class);
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
        String rendered = dto.toString();
        assertFalse(rendered.contains("s3cret"),
                "authorizationSecret must not appear in toString");
        assertFalse(rendered.contains("csr-payload-sentinel"),
                "the CSR payload must not appear in toString — the allowlist, not the field exclude, is what hides it");
    }

    @Test
    void builderToStringOmitsAuthorizationSecretAndCsr() {
        String rendered = ClientCertificateRekeyRequestDto.builder()
                .authorizationSecret("s3cret")
                .request("csr-payload-sentinel")
                .toString();
        assertFalse(rendered.contains("s3cret"),
                "the Lombok-generated builder has its own toString; it must not print the secret");
        assertFalse(rendered.contains("csr-payload-sentinel"),
                "the builder toString must not print the CSR payload either");
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
