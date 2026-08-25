package com.otilm.api.model.client.signing.profile.workflow.validation;

import com.otilm.api.model.common.validation.OidFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class OidValidator implements ConstraintValidator<ValidOid, String> {

    private static final Pattern OID_REGEX = Pattern.compile(OidFormat.REGEX);

    @Override
    public boolean isValid(String oid, ConstraintValidatorContext constraintValidatorContext) {
        // we do not validate null values
        if (oid == null) {
            return true;
        }
        return isValidOid(oid);
    }

    static boolean isValidOid(String oid) {
        return OID_REGEX.matcher(oid).matches();
    }
}
