package com.otilm.api.model.connector.cryptography.v2.operations.validation;

import com.otilm.api.model.connector.cryptography.v2.operations.data.SignatureRequestDataV2Dto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueSignatureIdentifiersValidator
        implements ConstraintValidator<UniqueSignatureIdentifiers, List<SignatureRequestDataV2Dto>> {

    @Override
    public boolean isValid(List<SignatureRequestDataV2Dto> items, ConstraintValidatorContext context) {
        if (items == null) {
            return true;
        }

        Set<String> identifiers = new HashSet<>();
        return items.stream()
                .filter(item -> item != null && item.getIdentifier() != null && !item.getIdentifier().isBlank())
                .map(SignatureRequestDataV2Dto::getIdentifier)
                .allMatch(identifiers::add);
    }
}
