package com.czertainly.api.model.common.validation;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValidEnumValueValidator implements ConstraintValidator<ValidEnumValue, String> {

    private Set<String> validValues;

    @Override
    public void initialize(ValidEnumValue constraintAnnotation) {
        if (IPlatformEnum.class.isAssignableFrom(constraintAnnotation.enumClass())) {
            validValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                    .map(item -> ((IPlatformEnum) item).getCode())
                    .collect(Collectors.toSet());
        } else {
            validValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                    .map(Enum::name)
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        return validValues.contains(value);
    }
}
