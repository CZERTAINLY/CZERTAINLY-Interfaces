package com.otilm.api.model.common.signature.parameters.pades.validation;

import com.otilm.api.model.common.signature.parameters.pades.PadesVisibleSignatureDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageAndMimeTypeTogetherValidator
        implements
            ConstraintValidator<ImageAndMimeTypeTogether, PadesVisibleSignatureDto> {

    @Override
    public boolean isValid(PadesVisibleSignatureDto value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        boolean valid = (value.getImage() == null) == (value.getImageMimeType() == null);
        if (!valid) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(value.getImage() == null ? "image" : "imageMimeType")
                    .addConstraintViolation();
        }
        return valid;
    }
}
