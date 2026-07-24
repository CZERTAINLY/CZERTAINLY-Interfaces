package com.otilm.api.model.connector.cryptography.v2.operations.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = UniqueSignatureIdentifiersValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UniqueSignatureIdentifiers {

    String message() default "identifiers must be unique within the batch";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
