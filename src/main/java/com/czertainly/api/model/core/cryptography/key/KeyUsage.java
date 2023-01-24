package com.czertainly.api.model.core.cryptography.key;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.connector.cryptography.enums.KeyType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum KeyUsage {
    SIGN(1, "sign", "Allow for signing. Applies to Sign operation. Valid for PGP Key, Private Key"),
    VERIFY(2, "verify", "Allow for signature verification. Applies to Signature Verify and Validate operations. Valid for PGP Key, Certificate and Public Key."),
    ENCRYPT(4, "encrypt", "Allow for encryption. Applies to Encrypt operation. Valid for PGP Key, Private Key, Public Key and Symmetric Key. Encryption for the purpose of wrapping is separate Wrap Key value."),
    DECRYPT(8, "decrypt", "Allow for decryption. Applies to Decrypt operation. Valid for PGP Key, Private Key, Public Key and Symmetric Key. Decryption for the purpose of unwrapping is separate Unwrap Key value."),
    WRAP(10, "wrap", "Allow for key wrapping. Applies to Get operation when wrapping is required by Wrapping Specification is provided on the object used to Wrap. Valid for PGP Key, Private Key and Symmetric Key. Note: even if the underlying wrapping mechanism is encryption, this value is logically separate."),
    UNWRAP(20, "unwrap", "Allow for key unwrapping. Applies to Get operation when unwrapping is required on the object used to Unwrap.  Valid for PGP Key, Private Key, Public Key and Symmetric Key. Not interchangeable with Decrypt. Note: even if the underlying unwrapping mechanism is decryption, this value is logically separate.");

    private static final KeyUsage[] VALUES;

    static {
        VALUES = values();
    }

    private final int id;
    private final String name;
    private final String description;

    KeyUsage(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Return a string representation of this status code.
     */
    @Override
    public String toString() {
        return this.id + " " + name();
    }

    /**
     * Return the {@code KeyType} enum constant with the specified id.
     * @param id the id of the enum to be returned
     * @return the enum constant with the specified id
     * @throws IllegalArgumentException if this enum has no constant for the specified id
     */
    public static KeyUsage valueOf(int id) {
        KeyUsage format = resolve(id);
        if (format == null) {
            throw new IllegalArgumentException("No matching constant for [" + id + "]");
        }
        return format;
    }

    /**
     * Resolve the given id to an {@code KeyUsage}, if possible.
     * @param id the id of the key type
     * @return the corresponding {@code KeyUsage}, or {@code null} if not found
     */
    @Nullable
    public static KeyUsage resolve(int id) {
        // Use cached VALUES instead of values() to prevent array allocation.
        for (KeyUsage format : VALUES) {
            if (format.id == id) {
                return format;
            }
        }
        return null;
    }

    @JsonCreator
    public static KeyUsage findByCode(String code) {
        return Arrays.stream(KeyUsage.values())
                .filter(k -> k.name.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown KeyUsage {}", code)));
    }

}
