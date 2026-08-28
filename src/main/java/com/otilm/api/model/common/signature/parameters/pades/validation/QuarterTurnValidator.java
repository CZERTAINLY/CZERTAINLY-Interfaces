package com.otilm.api.model.common.signature.parameters.pades.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QuarterTurnValidator implements ConstraintValidator<QuarterTurn, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value == null || value == 0 || value == 90 || value == 180 || value == 270;
    }
}
