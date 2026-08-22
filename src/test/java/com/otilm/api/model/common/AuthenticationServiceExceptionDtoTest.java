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

    @Test
    void getStatusCode_isStableAcrossRepeatedReads() {
        AuthenticationServiceExceptionDto dto = new AuthenticationServiceExceptionDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed parsing error from Authentication Service");

        dto.getStatusCode();

        // the second read is what separates a wrong return value from a mutating one
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), dto.getStatusCode());
    }

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
