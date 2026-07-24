package com.otilm.api.model.connector.v2.cryptography;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.lang.annotation.Annotation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public final class ValidationTestUtils {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    private ValidationTestUtils() {
    }

    public static Set<ConstraintViolation<Object>> validate(Object value) {
        return VALIDATOR.validate(value);
    }

    public static void assertViolation(
            Set<? extends ConstraintViolation<?>> violations,
            String propertyPath,
            Class<? extends Annotation> constraintType) {
        assertTrue(violations.stream().anyMatch(violation ->
                        violation.getPropertyPath().toString().equals(propertyPath)
                                && violation.getConstraintDescriptor().getAnnotation().annotationType()
                                .equals(constraintType)),
                () -> "Expected " + constraintType.getSimpleName() + " at " + propertyPath + " but found "
                        + violations.stream().map(violation ->
                        violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName()
                                + " at " + violation.getPropertyPath()).toList());
    }

    public static void assertNoViolations(Object value) {
        var violations = validate(value);
        assertTrue(violations.isEmpty(), () -> "Expected no violations but found " + violations);
    }
}
