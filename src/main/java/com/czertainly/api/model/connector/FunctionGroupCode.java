package com.czertainly.api.model.connector;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

public enum FunctionGroupCode {

    CREDENTIAL_PROVIDER("credentialProvider"),
    LEGACY_CA_CONNECTOR("legacyCaConnector"),
    CA_CONNECTOR("caConnector"),
    DISCOVERY_PROVIDER("discoveryProvider");

    @Schema(description = "Functional Group code of the Connector",
            example = "credentialProvider", required = true)
    private String code;

    FunctionGroupCode(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }

    @JsonCreator
    public static FunctionGroupCode findByCode(String code) {
        return Arrays.stream(FunctionGroupCode.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown function group code {}", code)));
    }
}
