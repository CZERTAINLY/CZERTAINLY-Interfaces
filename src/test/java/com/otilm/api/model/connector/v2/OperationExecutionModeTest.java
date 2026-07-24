package com.otilm.api.model.connector.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.connector.common.v2.OperationExecutionMode;
import com.otilm.api.model.connector.cryptography.v2.key.CreateKeyRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.DestroyKeyRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.SignDataRequestV2Dto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OperationExecutionModeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void serializesStableWireCodes() throws Exception {
        assertEquals("\"synchronous\"", MAPPER.writeValueAsString(OperationExecutionMode.SYNCHRONOUS));
        assertEquals(OperationExecutionMode.ASYNCHRONOUS,
                MAPPER.readValue("\"asynchronous\"", OperationExecutionMode.class));
    }

    @Test
    void executionModeIsRequiredOnEveryAsyncCapableRequest() {
        assertExecutionModeViolation(new CreateKeyRequestV2Dto());
        assertExecutionModeViolation(new DestroyKeyRequestV2Dto());
        assertExecutionModeViolation(new SignDataRequestV2Dto());
    }

    private static void assertExecutionModeViolation(Object request) {
        assertTrue(VALIDATOR.validate(request).stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("executionMode")));
    }
}
