package com.czertainly.api.model.core.discovery;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.core.connector.ConnectorStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

public enum DiscoveryStatus {

    IN_PROGRESS("inProgress"),
    FAILED("failed"),
    COMPLETED("completed"),
    WARNING("warning")
    ;
    @Schema(description = "Discovery Status",
            example = "completed", required = true)
    private String code;

    DiscoveryStatus(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }

    @JsonCreator
    public static DiscoveryStatus findByCode(String code) {
        return Arrays.stream(DiscoveryStatus.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown status {}", code)));
    }
}
