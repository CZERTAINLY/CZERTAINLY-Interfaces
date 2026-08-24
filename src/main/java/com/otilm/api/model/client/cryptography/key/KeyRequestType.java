package com.otilm.api.model.client.cryptography.key;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.otilm.api.exception.ValidationError;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.common.enums.IPlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

@Schema(enumAsRef = true)
public enum KeyRequestType implements IPlatformEnum {
    SECRET(Codes.SECRET, "Secret key"),
    KEY_PAIR(Codes.KEY_PAIR, "Key pair");

    public static final class Codes {
        public static final String SECRET = "secret";
        public static final String KEY_PAIR = "keyPair";

        private Codes() {
        }
    }

    private static final KeyRequestType[] VALUES;

    static {
        VALUES = values();
    }

    @Schema(description = "Type of the key to be generated", examples = {"secret"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private final String code;
    private final String label;
    private final String description;

    KeyRequestType(String code, String label) {
        this(code, label, null);
    }

    KeyRequestType(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    @JsonCreator
    public static KeyRequestType findByCode(String code) {
        return Arrays
                .stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(
                        () -> new ValidationException(ValidationError.create("Unknown key request type {}", code)));
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
