package com.czertainly.api.model.core.connector;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum ConnectorStatus {

    WAITING_FOR_APPROVAL("waitingForApproval"),
    CONNECTED("connected"),
    FAILED("failed"),
    OFFLINE("offline")
    ;

    @Schema(description = "Connector status",
            example = "connected", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    ConnectorStatus(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }

    @JsonCreator
    public static ConnectorStatus findByCode(String code) {
        return Arrays.stream(ConnectorStatus.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown status {}", code)));
    }
}
