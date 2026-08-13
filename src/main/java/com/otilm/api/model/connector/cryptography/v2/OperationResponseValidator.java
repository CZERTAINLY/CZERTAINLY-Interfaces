package com.otilm.api.model.connector.cryptography.v2;

import com.otilm.api.model.client.cryptography.key.KeyRequestType;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.connector.cryptography.v2.token.TokenStatusResponseV2Dto;
import com.otilm.api.model.core.cryptography.key.KeyUsage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.groups.Default;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
}
