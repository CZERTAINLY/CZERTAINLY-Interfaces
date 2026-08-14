package com.otilm.api.model.connector.cryptography.v2.operations.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueIdentifiersValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UniqueIdentifiers {

    String message() default "identifiers must be unique within the batch";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
