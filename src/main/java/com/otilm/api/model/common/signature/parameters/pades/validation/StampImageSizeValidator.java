package com.otilm.api.model.common.signature.parameters.pades.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StampImageSizeValidator implements ConstraintValidator<StampImageSize, byte[]> {

    private int min;
    private int max;

    @Override
    public void initialize(StampImageSize constraint) {
        this.min = constraint.min();
        this.max = constraint.max();
    }

    @Override
    public boolean isValid(byte[] value, ConstraintValidatorContext context) {
        return value == null || (value.length >= this.min && value.length <= this.max);
    }
}
