package com.czertainly.api.model.connector.cryptography.enums;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum KeyType implements IAbstractSearchableEnum {
    SECRET_KEY(1, "Secret key", "Symmetric secret key"),
    PUBLIC_KEY(2, "Public key", "Asymmetric public key"),
    PRIVATE_KEY(3, "Private key", "Asymmetric private key"),
    SPLIT_KEY(4, "Split key", "Secret or private key split into parts");

    private static final KeyType[] VALUES;

    static {
        VALUES = values();
    }

    private final int id;
    private final String name;
    private final String description;

    KeyType(int id, String name, String description) {
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
    public static KeyType valueOf(int id) {
        KeyType type = resolve(id);
        if (type == null) {
            throw new IllegalArgumentException("No matching constant for [" + id + "]");
        }
        return type;
    }

    /**
     * Resolve the given id to an {@code KeyType}, if possible.
     *
     * @param id the id of the key type
     * @return the corresponding {@code KeyType}, or {@code null} if not found
     */
    @Nullable
    public static KeyType resolve(int id) {
        // Use cached VALUES instead of values() to prevent array allocation.
        for (KeyType type : VALUES) {
            if (type.id == id) {
                return type;
            }
        }
        return null;
    }

    @JsonCreator
    public static KeyType findByCode(String code) {
        return Arrays.stream(KeyType.values())
                .filter(k -> k.name.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown KeyType {}", code)));
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
