package com.otilm.api.model.connector.discovery.v2.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class-level constraint enforcing the normative rule that key material never traverses
 * discovery: {@code publicKeyFormat} must never be a private-key format ({@code PRKI} or
 * {@code EPRKI}), and {@code publicKey}/{@code publicKeyFormat} must both be absent whenever
 * {@code type} denotes a key with no reportable public part ({@code PRIVATE_KEY},
 * {@code SECRET_KEY}, {@code SPLIT_KEY}).
 */
@Constraint(validatedBy = NoPrivateKeyMaterialValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoPrivateKeyMaterial {

    String message() default "key material must never traverse discovery: publicKey/publicKeyFormat "
            + "must be absent for this key type, and publicKeyFormat must never be a private-key format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
