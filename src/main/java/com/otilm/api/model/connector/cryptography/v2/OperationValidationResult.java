package com.otilm.api.model.connector.cryptography.v2;

import java.util.Objects;
import lombok.Getter;

/**
 * Result of validating an async-capable cryptographic operation response.
 */
@Getter
public final class OperationValidationResult {

    private static final OperationValidationResult VALID = new OperationValidationResult(null);

    private final IllegalArgumentException cause;

    private OperationValidationResult(IllegalArgumentException cause) {
        this.cause = cause;
    }

    public static OperationValidationResult valid() {
        return VALID;
    }

    public static OperationValidationResult invalid(IllegalArgumentException cause) {
        return new OperationValidationResult(Objects.requireNonNull(cause, "cause is required"));
    }

    public boolean isValid() {
        return cause == null;
    }

}
