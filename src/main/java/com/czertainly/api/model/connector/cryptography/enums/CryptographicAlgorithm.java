package com.czertainly.api.model.connector.cryptography.enums;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum CryptographicAlgorithm implements IPlatformEnum {

    RSA("RSA", "RSA", "Rivest–Shamir–Adleman"),
    ECDSA("ECDSA", "ECDSA", "Elliptic Curve Digital Signature Algorithm"),
    FALCON("FALCON", "FALCON", "Fast Fourier lattice-based compact signatures over NTRU"),
    DILITHIUM("CRYSTALS-Dilithium", "CRYSTALS-Dilithium", "Post-quantum lattice-based signature scheme"),
    SPHINCSPLUS("SPHINCS+", "SPHINCS+", "Post-quantum stateless hash-based signature scheme");

    private static final CryptographicAlgorithm[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;

    CryptographicAlgorithm(String code, String label) {
        this(code, label,null);
    }

    CryptographicAlgorithm(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }



    @JsonCreator
    public static CryptographicAlgorithm findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown Cryptographic Algorithm {}", code)));
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
}
