package com.otilm.api.model.connector.cryptography.v2.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TokenStatusV2Test {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void containsOnlyProviderObservableStatuses() {
        assertEquals(Set.of(
                        TokenStatusV2.CONNECTED,
                        TokenStatusV2.DISCONNECTED,
                        TokenStatusV2.WARNING,
                        TokenStatusV2.UNKNOWN),
                Set.of(TokenStatusV2.values()));

        assertThrows(ValidationException.class, () -> TokenStatusV2.findByCode("Activated"));
        assertThrows(ValidationException.class, () -> TokenStatusV2.findByCode("Deactivated"));
    }

    @Test
    void preservesTheExistingCodesForRemainingStatuses() throws Exception {
        assertEquals("\"Connected\"", MAPPER.writeValueAsString(TokenStatusV2.CONNECTED));
        assertEquals(TokenStatusV2.CONNECTED, MAPPER.readValue("\"Connected\"", TokenStatusV2.class));
    }
}
