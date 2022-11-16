package com.czertainly.api.model.core.connector;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum FunctionGroupCode {

    CREDENTIAL_PROVIDER("credentialProvider"),
    LEGACY_AUTHORITY_PROVIDER("legacyAuthorityProvider"),
    AUTHORITY_PROVIDER("authorityProvider"),
    DISCOVERY_PROVIDER("discoveryProvider"),
    ENTITY_PROVIDER("entityProvider"),
    COMPLIANCE_PROVIDER("complianceProvider");

    @Schema(description = "Function Group code of the Connector",
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
