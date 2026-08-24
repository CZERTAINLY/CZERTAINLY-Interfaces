package com.otilm.api.model.client.signing.profile.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class-level constraint that checks a Signing Profile's workflow against its signing scheme: ILM-managed signing must
 * carry the fields the platform needs, and delegated signing must omit the ones it would never honour. Each violation
 * is reported on its own property path.
 */
@Constraint(validatedBy = ManagedSignatureFormattingConnectorValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidManagedSignatureFormattingConnector {
    String message() default "signatureFormattingConnectorUuid must be provided when using ILM-managed signing with a Timestamping or Content Signing workflow";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
