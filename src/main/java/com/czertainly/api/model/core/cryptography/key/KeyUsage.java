package com.czertainly.api.model.core.cryptography.key;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum KeyUsage implements IPlatformEnum {
    // TODO: use bit masking to store value and change 10 and 20 to 16 and 32 to be powers of 2
    SIGN("sign", "Sign", "Allow for signing. Applies to Sign operation. Valid for PGP Key, Private Key", 1),
    VERIFY("verify", "Verify", "Allow for signature verification. Applies to Signature Verify and Validate operations. Valid for PGP Key, Certificate and Public Key.", 2),
    ENCRYPT("encrypt", "Encrypt", "Allow for encryption. Applies to Encrypt operation. Valid for PGP Key, Private Key, Public Key and Symmetric Key. Encryption for the purpose of wrapping is separate Wrap Key value.",4),
    DECRYPT("decrypt", "Decrypt", "Allow for decryption. Applies to Decrypt operation. Valid for PGP Key, Private Key, Public Key and Symmetric Key. Decryption for the purpose of unwrapping is separate Unwrap Key value.", 8),
    WRAP("wrap", "Wrap key", "Allow for key wrapping. Applies to Get operation when wrapping is required by Wrapping Specification is provided on the object used to Wrap. Valid for PGP Key, Private Key and Symmetric Key. Note: even if the underlying wrapping mechanism is encryption, this value is logically separate.", 10),
    UNWRAP("unwrap", "Unwrap key", "Allow for key unwrapping. Applies to Get operation when unwrapping is required on the object used to Unwrap.  Valid for PGP Key, Private Key, Public Key and Symmetric Key. Not interchangeable with Decrypt. Note: even if the underlying unwrapping mechanism is decryption, this value is logically separate.", 20);

    private static final KeyUsage[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;
    private final int bitmask;

    KeyUsage(String code, String label, int bitmask) {
        this(code, label, null, bitmask);
    }

    KeyUsage(String code, String label, String description, int bitmask) {
        this.code = code;
        this.label = label;
        this.description = description;
        this.bitmask = bitmask;
    }

    @Override
    @JsonValue
    public String getCode() {
        return this.code;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public int getBitmask() {
        return bitmask;
    }

    /**
     * Return the {@code KeyType} enum constant with the specified bitmask.
     * @param bitmask bitmask of the enum value to be returned
     * @return the enum constant with the specified bitmask
     * @throws IllegalArgumentException if this enum has no constant for the specified bitmask
     */
    public static KeyUsage valueOf(int bitmask) {
        KeyUsage format = resolve(bitmask);
        if (format == null) {
            throw new IllegalArgumentException("No matching constant for [" + bitmask + "]");
        }
        return format;
    }

    /**
     * Resolve the given bitmask to an {@code KeyUsage}, if possible.
     * @param bitmask bitmask of the key usage
     * @return the corresponding {@code KeyUsage}, or {@code null} if not found
     */
    @Nullable
    public static KeyUsage resolve(int bitmask) {
        // Use cached VALUES instead of values() to prevent array allocation.
        for (KeyUsage usage : VALUES) {
            if (usage.bitmask == bitmask) {
                return usage;
            }
        }
        return null;
    }

    @JsonCreator
    public static KeyUsage findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown key usage {}", code)));
    }
}
