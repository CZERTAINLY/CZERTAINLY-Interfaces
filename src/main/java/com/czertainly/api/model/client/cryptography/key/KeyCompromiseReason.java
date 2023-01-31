package com.czertainly.api.model.client.cryptography.key;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum KeyCompromiseReason {
    UNAUTHORIZED_DISCLOSURE("Unauthorized Disclosure"),
    UNAUTHORIZED_MODIFICATION("Unauthorized Modification"),

    UNAUTHORIZED_SUBSTITUTION("Unauthorized Substitution"),

    UNAUTHORIZED_USE_OF_SENSITIVE_DATA("Unauthorized use of sensitive data");
    @Schema(description = "Reason for compromise",
            example = "Unauthorized Disclosure", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String code;

    KeyCompromiseReason(String code) {
        this.code = code;
    }

    @JsonCreator
    public static KeyCompromiseReason findByCode(String code) {
        return Arrays.stream(KeyCompromiseReason.values())
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
