package com.otilm.api.model.connector.cryptography.v2;

import com.otilm.api.model.client.cryptography.key.KeyRequestType;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.connector.common.v2.OperationExecutionMode;
import com.otilm.api.model.connector.cryptography.v2.key.KeyCreationResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyCreationStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyDestructionStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.token.TokenStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.validation.AsynchronousResponse;
import com.otilm.api.model.connector.cryptography.v2.validation.SynchronousResponse;
import com.otilm.api.model.core.cryptography.key.KeyUsage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.groups.Default;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Validates that an async-capable connector response honors the caller-selected execution mode.
 */
public final class OperationResponseValidator {

    private final Validator validator;

    public OperationResponseValidator(Validator validator) {
        this.validator = Objects.requireNonNull(validator, "validator is required");
    }

    private static OperationValidationResult validate(Runnable validation) {
        try {
            validation.run();
            return OperationValidationResult.valid();
        } catch (IllegalArgumentException e) {
            return OperationValidationResult.invalid(e);
        }
    }

    private static void requireExecutionMode(OperationExecutionMode mode) {
        if (mode == null) {
            throw new IllegalArgumentException("Execution mode is required");
        }
    }

    private static void requireResponseStatus(ResponseEntity<?> response, HttpStatus expectedStatus) {
        if (response == null) {
            throw new IllegalArgumentException("Connector returned no response");
        }
        if (response.getStatusCode().value() != expectedStatus.value()) {
            throw new IllegalArgumentException("Connector returned HTTP " + response.getStatusCode().value()
                    + "; expected HTTP " + expectedStatus.value());
        }
    }

    private static <T> T requireBody(ResponseEntity<T> response) {
        if (response.getBody() == null) {
            throw new IllegalArgumentException("Connector response body is required");
        }
        return response.getBody();
    }

    private static void requireEmptyBody(ResponseEntity<?> response) {
        if (response.getBody() != null) {
            throw new IllegalArgumentException("Connector response body must be empty");
        }
    }

    public OperationValidationResult validateTokenStatus(TokenStatusResponseV2Dto response) {
        return validateBeanConstraints(response);
    }

    public OperationValidationResult validateAttributeList(List<BaseAttribute> response) {
        return validateResponseElements(response, "attribute");
    }

    public OperationValidationResult validateKeyUsageList(List<KeyUsage> response) {
        return validateResponseElements(response, "key usage");
    }

    public OperationValidationResult validateSupportedKeyRequestTypes(List<KeyRequestType> response) {
        return validateResponseElements(response, "key request type");
    }

    public OperationValidationResult validateCreateKey(OperationExecutionMode mode,
            ResponseEntity<? extends KeyCreationResponseV2Dto> response) {
        return validateOperationResponse(mode, response, SynchronousBody.REQUIRED);
    }

    public OperationValidationResult validateCreateKeyStatus(KeyCreationStatusResponseV2Dto response) {
        return validateBeanConstraints(response);
    }

    public OperationValidationResult validateDestroy(OperationExecutionMode mode,
            ResponseEntity<KeyOperationResponseV2Dto> response) {
        return validateOperationResponse(mode, response, SynchronousBody.EMPTY);
    }

    private OperationValidationResult validateOperationResponse(OperationExecutionMode mode, ResponseEntity<?> response,
            SynchronousBody synchronousBody) {
        return validate(() -> {
            requireExecutionMode(mode);
            if (mode == OperationExecutionMode.SYNCHRONOUS) {
                requireResponseStatus(response, HttpStatus.OK);
                if (synchronousBody == SynchronousBody.REQUIRED) {
                    validateBean(requireBody(response), SynchronousResponse.class);
                } else {
                    requireEmptyBody(response);
                }
            } else {
                requireResponseStatus(response, HttpStatus.ACCEPTED);
                validateBean(requireBody(response), AsynchronousResponse.class);
            }
        });
    }

    public OperationValidationResult validateDestroyKeyStatus(KeyDestructionStatusResponseV2Dto response) {
        return validateBeanConstraints(response);
    }

    private OperationValidationResult validateBeanConstraints(Object response) {
        return validate(() -> {
            if (response == null) {
                throw new IllegalArgumentException("Connector response body is required");
            }
            validateBean(response, Default.class);
        });
    }

    private OperationValidationResult validateResponseElements(List<?> response, String itemName) {
        return validate(() -> {
            if (response == null) {
                throw new IllegalArgumentException("Connector response body is required");
            }
            for (Object item : response) {
                if (item == null) {
                    throw new IllegalArgumentException("Connector response must not contain a null " + itemName);
                }
                validateBean(item, Default.class);
            }
        });
    }

    private void validateBean(Object body, Class<?> group) {
        Set<ConstraintViolation<Object>> violations = validator.validate(body, group);
        if (!violations.isEmpty()) {
            String details = violations
                    .stream()
                    .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                    .sorted()
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException("Connector response validation failed: " + details);
        }
    }

    private enum SynchronousBody {
        REQUIRED,
        EMPTY
    }
}
