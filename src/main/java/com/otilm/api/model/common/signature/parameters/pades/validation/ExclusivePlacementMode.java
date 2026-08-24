package com.otilm.api.model.common.signature.parameters.pades.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class-level constraint: a placement addresses the page in one mode only — a named field, coordinates, or an anchor.
 *
 * <p>
 * Exclusivity only. Completeness is decided after the request is merged over the profile default, because the same type
 * is also a partial override fragment.
 * </p>
 */
@Constraint(validatedBy = ExclusivePlacementModeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExclusivePlacementMode {

    String message() default "placement uses one addressing mode only: a named field, coordinates, or an anchor";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
