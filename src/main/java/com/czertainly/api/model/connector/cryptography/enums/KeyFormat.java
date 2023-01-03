package com.czertainly.api.model.connector.cryptography.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

@Schema(enumAsRef = true)
public enum KeyFormat {
    RAW(1, "Raw", "Encoded key in raw format"),
    SPKI(7, "SubjectPublicKeyInfo", "DER-encoded ASN.1 SubjectPublicKeyInfo of the public key"),
    PRKI(5, "PrivateKeyInfo", "DER-encoded ASN.1 PrivateKeyInfo of the private key"),
    EPRKI(5, "EncryptedPrivateKeyInfo", "DER-encoded ASN.1 EncryptedPrivateKeyInfo of the private key"),
    SPECIFIC(6, "Specific", "Specific encoded format of the key");

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

    public int getId() {
        return id;
    }

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
    public static KeyFormat valueOf(int id) {
        KeyFormat format = resolve(id);
        if (format == null) {
            throw new IllegalArgumentException("No matching constant for [" + id + "]");
        }
        return format;
    }

    /**
     * Resolve the given id to an {@code KeyType}, if possible.
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

}
