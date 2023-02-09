package com.czertainly.api.model.connector.cryptography.enums;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum KeyFormat implements IAbstractSearchableEnum{
    RAW(1, "Raw", "Encoded key in raw format"),
    SPKI(2, "SubjectPublicKeyInfo", "DER-encoded ASN.1 SubjectPublicKeyInfo of the public key"),
    PRKI(3, "PrivateKeyInfo", "DER-encoded ASN.1 PrivateKeyInfo of the private key"),
    EPRKI(4, "EncryptedPrivateKeyInfo", "DER-encoded ASN.1 EncryptedPrivateKeyInfo of the private key"),
    CUSTOM(99, "Custom", "Custom, external, specific data");

    private static final KeyFormat[] VALUES;

    static {
        VALUES = values();
    }

    private final int id;
    private final String name;
    private final String description;

    KeyFormat(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * Return the {@code KeyType} enum constant with the specified id.
     *
     * @param id the id of the enum to be returned
     * @return the enum constant with the specified id
     * @throws IllegalArgumentException if this enum has no constant for the specified id
     */
    public static KeyFormat valueOf(int id) {
        KeyFormat format = resolve(id);
        if (format == null) {
            throw new IllegalArgumentException("No matching constant for [" + id + "]");
        }
        return format;
    }

    /**
     * Resolve the given id to an {@code KeyType}, if possible.
     *
     * @param id the id of the key type
     * @return the corresponding {@code KeyType}, or {@code null} if not found
     */
    @Nullable
    public static KeyFormat resolve(int id) {
        // Use cached VALUES instead of values() to prevent array allocation.
        for (KeyFormat format : VALUES) {
            if (format.id == id) {
                return format;
            }
        }
        return null;
    }

    @JsonCreator
    public static KeyFormat findByCode(String code) {
        return Arrays.stream(KeyFormat.values())
                .filter(k -> k.name.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown Key Format {}", code)));
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

    @Override
    public String getEnumLabel() {
        return name;
    }

    /**
     * Return a string representation of this status code.
     */
    @Override
    public String toString() {
        return name();
    }

}
