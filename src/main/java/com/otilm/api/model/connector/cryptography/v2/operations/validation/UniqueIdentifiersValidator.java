package com.otilm.api.model.connector.cryptography.v2.operations.validation;

import com.otilm.api.model.connector.cryptography.v2.operations.data.IdentifiedDataV2Dto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UniqueIdentifiersValidator
        implements
            ConstraintValidator<UniqueIdentifiers, List<? extends IdentifiedDataV2Dto>> {

    @Override
    public boolean isValid(List<? extends IdentifiedDataV2Dto> items, ConstraintValidatorContext context) {
        if (items == null) {
            return true;
        }

        boolean valid = true;
        Map<String, Integer> firstIndexByIdentifier = new HashMap<>();
        for (int index = 0; index < items.size(); index++) {
            IdentifiedDataV2Dto item = items.get(index);
            if (item == null || item.getIdentifier() == null || item.getIdentifier().isBlank()) {
                continue;
            }

            Integer previousIndex = firstIndexByIdentifier.putIfAbsent(item.getIdentifier(), index);
            if (previousIndex != null) {
                context.disableDefaultConstraintViolation();
                context
                        .buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()
                                + "; duplicates item at index " + previousIndex)
                        .addPropertyNode("identifier")
                        .inIterable()
                        .atIndex(index)
                        .addConstraintViolation();
                valid = false;
            }
        }
        return valid;
    }
}
