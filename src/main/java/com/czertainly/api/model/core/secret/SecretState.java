package com.czertainly.api.model.core.secret;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum SecretState implements IPlatformEnum {

    INACTIVE("INACTIVE", "Inactive", "Secret is managed but not active and is not available to use"),
    ACTIVE("ACTIVE", "Active", "Secret is active and available for use"),
    EXPIRED("EXPIRED", "Expired", "Secret has expired and is no longer valid"),
    REVOKED("REVOKED", "Revoked", "Secret has been revoked and is no longer valid");
    ;

    private final String code;
    private final String label;
    private final String description;

    SecretState(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
