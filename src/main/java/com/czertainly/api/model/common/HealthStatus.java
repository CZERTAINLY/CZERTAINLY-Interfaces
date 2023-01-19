package com.czertainly.api.model.common;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum HealthStatus {
    OK("ok"),
    NOK("nok"),
    UNKNOWN("unknown");

    @Schema(description = "Health Status",
            example = "ok", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    HealthStatus(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }

    @JsonCreator
    public static HealthStatus findByCode(String code) {
        return Arrays.stream(HealthStatus.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown status {}", code)));
    }
}
