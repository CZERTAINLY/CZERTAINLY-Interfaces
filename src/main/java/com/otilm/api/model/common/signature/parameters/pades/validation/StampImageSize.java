package com.otilm.api.model.common.signature.parameters.pades.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A stamp image bounded in decoded bytes, so the schema is free to publish the same cap in base64 characters.
 *
 * <p>
 * {@code @Size} cannot do both: on a {@code byte[]} it enforces decoded bytes but publishes a {@code maxLength} a
 * quarter smaller over base64 text, overwriting any {@code maxLength} the schema states.
 * </p>
 */
@Constraint(validatedBy = StampImageSizeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StampImageSize {

    int min() default 1;

    int max();

    String message() default "image must be between {min} and {max} bytes";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
