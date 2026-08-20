package com.otilm.api.model.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthenticationServiceExceptionDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void getStatusCode_returnsTheStatusThatWasSet() {
        AuthenticationServiceExceptionDto dto = new AuthenticationServiceExceptionDto();
        dto.setStatusCode(HttpStatus.FORBIDDEN.value());

        assertEquals(HttpStatus.FORBIDDEN.value(), dto.getStatusCode());
    }

    /**
     * A getter that assigns to its own field both returns the wrong value and destroys the stored one, so a second read
     * is what distinguishes a wrong return value from a mutating one.
     */
    @Test
    void getStatusCode_isStableAcrossRepeatedReads() {
        AuthenticationServiceExceptionDto dto = new AuthenticationServiceExceptionDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed parsing error from Authentication Service");

        dto.getStatusCode();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), dto.getStatusCode());
    }

    /**
     * An unset status must still yield a usable code; {@link AuthenticationServiceExceptionDto#getStatusCode()} carries
     * why.
     */
    @Test
    void getStatusCode_fallsBackToBadRequestWhenUnset() {
        assertEquals(HttpStatus.BAD_REQUEST.value(), new AuthenticationServiceExceptionDto().getStatusCode());
    }

    @Test
    void getStatusCode_survivesAJsonRoundTrip() throws Exception {
        AuthenticationServiceExceptionDto original = new AuthenticationServiceExceptionDto(
                HttpStatus.UNAUTHORIZED.value(), "ACCESS_DENIED", "Access Denied");

        AuthenticationServiceExceptionDto restored = mapper
                .readValue(mapper.writeValueAsString(original), AuthenticationServiceExceptionDto.class);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), restored.getStatusCode());
        assertEquals("ACCESS_DENIED", restored.getCode());
        assertEquals("Access Denied", restored.getMessage());
    }
}
