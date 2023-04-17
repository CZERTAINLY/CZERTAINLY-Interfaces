package com.czertainly.api.model.connector.cryptography.enums;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum CryptographicAlgorithm implements IAbstractSearchableEnum {
    RSA(1, "RSA", "Rivest–Shamir–Adleman"),
    ECDSA(2, "ECDSA", "Elliptic Curve Digital Signature Algorithm"),
    FALCON(3, "FALCON", "Fast Fourier lattice-based compact signatures over NTRU"),
    DILITHIUM(4, "CRYSTALS-Dilithium", "Post-quantum lattice-based signature scheme"),
    SPHINCSPLUS(5, "SPHINCS+", "Post-quantum stateless hash-based signature scheme");

    private static final CryptographicAlgorithm[] VALUES;

    static {
        VALUES = values();
    }

    private final String name;
    private final int id;
    private final String description;

    CryptographicAlgorithm(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * Return the {@code CryptographicAlgorithm} enum constant with the specified id.
     *
     * @param id the id of the enum to be returned
     * @return the enum constant with the specified id
     * @throws IllegalArgumentException if this enum has no constant for the specified id
     */
    public static CryptographicAlgorithm valueOf(int id) {
        CryptographicAlgorithm alg = resolve(id);
        if (alg == null) {
            throw new IllegalArgumentException("No matching constant for [" + id + "]");
        }
        return alg;
    }

    /**
     * Resolve the given id to an {@code CryptographicAlgorithm}, if possible.
     *
     * @param id the id of the algorithm
     * @return the corresponding {@code CryptographicAlgorithm}, or {@code null} if not found
     */
    @Nullable
    public static CryptographicAlgorithm resolve(int id) {
        // Use cached VALUES instead of values() to prevent array allocation.
        for (CryptographicAlgorithm alg : VALUES) {
            if (alg.id == id) {
                return alg;
            }
        }
        return null;
    }

    @JsonCreator
    public static CryptographicAlgorithm findByCode(String code) {
        return Arrays.stream(CryptographicAlgorithm.values())
                .filter(k -> k.name.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown Algorithm {}", code)));
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
        return name();
    }

    @Override
    public String getEnumLabel() {
        return name;
    }
}
