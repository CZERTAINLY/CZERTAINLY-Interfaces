package com.czertainly.api.model.common.enums.cryptography;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum KeyAlgorithm implements IPlatformEnum {

    RSA("RSA", "RSA", "Rivest–Shamir–Adleman"),
    ECDSA("ECDSA", "ECDSA", "Elliptic Curve Digital Signature Algorithm"),
    FALCON("FALCON", "FALCON", "Fast Fourier lattice-based compact signatures over NTRU"),
    DILITHIUM("CRYSTALS-Dilithium", "CRYSTALS-Dilithium", "Post-quantum lattice-based signature scheme"),
    SPHINCSPLUS("SPHINCS+", "SPHINCS+", "Post-quantum stateless hash-based signature scheme"),
    UNKNOWN("Unknown", "Unknown", "Key algorithm not recognized");

    private static final KeyAlgorithm[] VALUES;

    static {
        VALUES = values();
    }

    @Schema(description = "Cryptographic algorithm code",
            example = "RSA", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String code;
    private final String label;
    private final String description;

    KeyAlgorithm(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
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

    @JsonCreator
    public static KeyAlgorithm findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown cryptographic algorithm code {}", code)));
    }
}
