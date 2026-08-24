package com.otilm.api.model.common.signature.parameters.pades.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A rotation in whole quarter turns. The renderer turns a stamp only in quarter turns, so accepting arbitrary degrees
 * would promise a rotation no connector can honour.
 */
@Constraint(validatedBy = QuarterTurnValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QuarterTurn {

    String message() default "rotation must be one of 0, 90, 180 or 270 degrees";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
