package com.otilm.api.model.connector.discovery.v2.validation;

import com.otilm.api.model.common.enums.cryptography.KeyType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;

/**
 * Class-level constraint enforcing the normative rule that key material never traverses discovery:
 * {@code publicKeyFormat} must never be a private-key format ({@code PRKI} or {@code EPRKI}), and
 * {@code publicKey}/{@code publicKeyFormat} must both be absent whenever {@code type} denotes a key with no reportable
 * public part ({@code PRIVATE_KEY}, {@code SECRET_KEY}, {@code SPLIT_KEY}).
 */
@Constraint(validatedBy = NoPrivateKeyMaterialValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoPrivateKeyMaterial {

    /**
     * The key types with no reportable public part, as the validator enforces them.
     *
     * <p>
     * Kept next to {@link #TYPES_WITHOUT_A_PUBLIC_PART_NAMES}, which is the same list as the published contract reads
     * it: an annotation value cannot be computed, so the two are declared together and
     * {@code DiscoveredKeyDtoValidationTest} pins that they name the same types. Every {@code @Schema} description that
     * states the rule draws its wording from the names constant, so a connector author reading only the generated
     * document sees the list the validator applies.
     */
    Set<KeyType> TYPES_WITHOUT_A_PUBLIC_PART = Set.of(KeyType.PRIVATE_KEY, KeyType.SECRET_KEY, KeyType.SPLIT_KEY);

    /** {@link #TYPES_WITHOUT_A_PUBLIC_PART} spelled for contract prose. Keep the two in step. */
    String TYPES_WITHOUT_A_PUBLIC_PART_NAMES = "PRIVATE_KEY, SECRET_KEY or SPLIT_KEY";

    String message() default "key material must never traverse discovery: publicKey/publicKeyFormat "
            + "must be absent for this key type, and publicKeyFormat must never be a private-key format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
