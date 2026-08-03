package com.otilm.api.model.core.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ClientCertificateRenewRequestDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void authorizationSecretIsAcceptedOnInputButNeverSerialized() throws Exception {
        ClientCertificateRenewRequestDto dto =
                mapper.readValue("{\"authorizationSecret\":\"s3cret\"}", ClientCertificateRenewRequestDto.class);
        assertEquals("s3cret", dto.getAuthorizationSecret());

        String json = mapper.writeValueAsString(dto);
        assertFalse(json.contains("authorizationSecret"),
                "write-only authorizationSecret must never be serialized back to a client");
        assertFalse(json.contains("s3cret"));
    }

    @Test
    void toStringOmitsAuthorizationSecret() {
        ClientCertificateRenewRequestDto dto = new ClientCertificateRenewRequestDto();
        dto.setAuthorizationSecret("s3cret");
        assertFalse(dto.toString().contains("s3cret"),
                "authorizationSecret is @ToString.Exclude and must not appear in the generated toString");
    }

    @Test
    void authorizationSecretIsExcludedFromEqualsAndHashCode() {
        ClientCertificateRenewRequestDto a = new ClientCertificateRenewRequestDto();
        a.setAuthorizationSecret("secret-a");
        ClientCertificateRenewRequestDto b = new ClientCertificateRenewRequestDto();
        b.setAuthorizationSecret("secret-b");
        assertEquals(a, b, "authorizationSecret must not affect equals()");
        assertEquals(a.hashCode(), b.hashCode(), "authorizationSecret must not affect hashCode()");
    }
}
