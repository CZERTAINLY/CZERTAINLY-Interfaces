package com.otilm.api.model.common.signature.parameters.pades.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class-level constraint: the stamp image and its MIME type are one unit. Bytes without a type are bytes nobody can
 * decode, and a type without bytes describes nothing.
 */
@Constraint(validatedBy = ImageAndMimeTypeTogetherValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ImageAndMimeTypeTogether {

    String message() default "image and imageMimeType must be supplied together";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
