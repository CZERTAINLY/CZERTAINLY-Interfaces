package com.czertainly.api.model.core.cryptography.key;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.cryptography.key.KeyRequestType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

public enum KeyState {
    PRE_ACTIVE("secret"),
    ACTIVE("keyPair"),
    DEACTIVATED("deactivated"),
    COMPROMISED("compromised"),
    DESTROYED("destroyed"),
    COMPROMISED_DESTROYED("compromisedDestroyed");

    @Schema(description = "Type of the key to be generated",
            example = "secret", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String code;

    KeyState(String code) {
        this.code = code;
    }

    @JsonCreator
    public static KeyState findByCode(String code) {
        return Arrays.stream(KeyState.values())
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
