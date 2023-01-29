package com.czertainly.api.model.client.cryptography.key;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum KeyRequestType {
    SECRET("secret"),
    KEY_PAIR("keyPair");

    @Schema(description = "Type of the key to be generated",
            example = "secret", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String code;

    KeyRequestType(String code) {
        this.code = code;
    }

    @JsonCreator
    public static KeyRequestType findByCode(String code) {
        return Arrays.stream(KeyRequestType.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown code {}", code)));
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }
}
