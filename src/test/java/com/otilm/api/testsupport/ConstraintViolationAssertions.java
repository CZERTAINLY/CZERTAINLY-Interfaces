package com.otilm.api.testsupport;

import jakarta.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Shared assertions over Bean Validation results, for the tests that hold a request schema to its field rules.
 *
 * <p>
 * Validation tests must use this class rather than declare their own copies: a per-test copy of the failure rendering
 * drifts, and the message a failing test prints is the only place the violations that did fire are visible.
 * </p>
 */
public final class ConstraintViolationAssertions {

    private ConstraintViolationAssertions() {
    }

    /**
     * Fails unless {@code violations} carries exactly the given message on the given property path.
     *
     * @param violations the violations a validator produced
     * @param path the property path the violation is expected on
     * @param message the message the violation is expected to carry
     */
    public static void assertHasViolation(Set<? extends ConstraintViolation<?>> violations, String path,
            String message) {
        assertTrue(
                violations
                        .stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals(path) && v.getMessage().equals(message)),
                () -> "Expected " + path + ": " + message + ", got " + render(violations));
    }

    /**
     * Fails if {@code violations} is not empty, naming what did fire.
     *
     * @param violations the violations a validator produced
     */
    public static void assertNoViolations(Set<? extends ConstraintViolation<?>> violations) {
        assertTrue(violations.isEmpty(), () -> "expected no violations, got " + render(violations));
    }

    /**
     * The violations as one line of {@code path: message} entries, for a failure message.
     *
     * @param violations the violations a validator produced
     * @return the rendered violations
     */
    public static String render(Set<? extends ConstraintViolation<?>> violations) {
        return violations.stream().map(v -> v.getPropertyPath() + ": " + v.getMessage()).toList().toString();
    }
}
