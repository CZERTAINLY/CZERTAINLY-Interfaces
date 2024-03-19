package com.czertainly.api.model.connector.cryptography.enums;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum TokenInstanceStatus implements IPlatformEnum {
    CONNECTED("Connected", "Connected"),
    DISCONNECTED("Disconnected", "Disconnected"),
    ACTIVATED("Activated", "Activated"),
    DEACTIVATED("Deactivated", "Deactivated"),
    WARNING("Warning", "Warning"),
    UNKNOWN("Unknown", "Unknown");

    private static final TokenInstanceStatus[] VALUES;

    static {
        VALUES = values();
    }

    @Schema(description = "Token instance status",
            example = "ok", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String code;
    private final String label;
    private final String description;

    TokenInstanceStatus(String code, String label) {
        this(code, label,null);
    }

    TokenInstanceStatus(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    @JsonCreator
    public static TokenInstanceStatus findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown token instance status {}", code)));
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
