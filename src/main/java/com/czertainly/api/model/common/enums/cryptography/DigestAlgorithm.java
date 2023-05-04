package com.czertainly.api.model.common.enums.cryptography;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum DigestAlgorithm implements IPlatformEnum {
    MD5("MD5","MD5", "Message Digest algorithm"),
    SHA_1("SHA-1","SHA-1", "Secure hash algorithm 1"),
    SHA_224("SHA-224","SHA-224", "Secure hash algorithm 2 with digest length of 224 bits"),
    SHA_256("SHA-256", "SHA-256", "Secure hash algorithm 2 with digest length of 256 bits"),
    SHA_384("SHA-384", "SHA-384", "Secure hash algorithm 2 with digest length of 384 bits"),
    SHA_512("SHA-512", "SHA-512", "Secure hash algorithm 2 with digest length of 512 bits"),
    SHA3_256("SHA3-256", "SHA3-256", "Secure hash algorithm 3 with digest length of 256 bits"),
    SHA3_384("SHA3-384", "SHA3-384", "Secure hash algorithm 3 with digest length of 384 bits"),
    SHA3_512("SHA3-512", "SHA3-512", "Secure hash algorithm 3 with digest length of 512 bits");

    private static final DigestAlgorithm[] VALUES;

    static {
        VALUES = values();
    }

    @Schema(description = "Digest algorithm code",
            example = "SHA-1", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String code;
    private final String label;
    private final String description;

    DigestAlgorithm(String code, String label, String description) {
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
    public static DigestAlgorithm findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown digest algorithm code {}", code)));
    }
}
