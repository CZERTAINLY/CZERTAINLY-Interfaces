package com.czertainly.api.model.connector.cryptography.enums;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum TokenInstanceStatus {
    CONNECTED("Connected"),
    DISCONNECTED("Disconnected"),
    ACTIVATED("Activated"),
    DEACTIVATED("Deactivated"),
    WARNING("Warning"),
    UNKNOWN("Unknown");

    @Schema(description = "Token instance status",
            example = "ok", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String code;

    TokenInstanceStatus(String code) {
        this.code = code;
    }

    @JsonCreator
    public static TokenInstanceStatus findByCode(String code) {
        return Arrays.stream(TokenInstanceStatus.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown status {}", code)));
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }
}
