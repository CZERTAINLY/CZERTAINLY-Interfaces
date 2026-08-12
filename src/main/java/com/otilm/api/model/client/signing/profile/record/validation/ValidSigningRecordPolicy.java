package com.otilm.api.model.client.signing.profile.record.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SigningRecordPolicyValidator.class)
@Documented
public @interface ValidSigningRecordPolicy {
    String message() default "recordSignedDocument is only valid for DOCUMENT_SIGNING and TIMESTAMPING workflows";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
