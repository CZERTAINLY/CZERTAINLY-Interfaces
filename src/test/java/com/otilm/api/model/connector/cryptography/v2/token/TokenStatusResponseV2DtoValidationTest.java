package com.otilm.api.model.connector.cryptography.v2.token;

import com.otilm.api.testsupport.ValidatorFixture;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenStatusResponseV2DtoValidationTest {

    @AutoClose
    private static final ValidatorFixture VALIDATORS = new ValidatorFixture();
    private static final Validator VALIDATOR = VALIDATORS.validator();

    @Test
    void validate_hasNoViolations_forValidTokenStatus() {
        // given
        TokenStatusResponseV2Dto response = new TokenStatusResponseV2Dto();
        response.setStatus(TokenStatusV2.CONNECTED);

        // when
        Set<ConstraintViolation<TokenStatusResponseV2Dto>> violations = VALIDATOR.validate(response);

        // then
        assertTrue(violations.isEmpty());
    }

    @Test
    void validate_requiresStatus_forMissingTokenStatus() {
        // given
        TokenStatusResponseV2Dto responseWithoutStatus = new TokenStatusResponseV2Dto();

        // when
        Set<ConstraintViolation<TokenStatusResponseV2Dto>> violations = VALIDATOR.validate(responseWithoutStatus);

        // then
        ConstraintViolation<TokenStatusResponseV2Dto> violation = violations.iterator().next();
        assertEquals("status", violation.getPropertyPath().toString());
        assertEquals("status is required", violation.getMessage());
    }
}
