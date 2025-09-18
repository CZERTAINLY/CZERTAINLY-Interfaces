package com.czertainly.api.model.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidEnumValueValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnumValue {
    Class<? extends Enum<?>> enumClass();
    String message() default "Value is not valid for specified enum.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
