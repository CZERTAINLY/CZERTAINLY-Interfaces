package com.czertainly.api.model.core.admin;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.core.discovery.DiscoveryStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

public enum AdminRole {

    ADMINISTRATOR("administrator"),
    SUPERADMINISTRATOR("superAdministrator");

    @Schema(description = "Administrator Role",
            example = "administrator", required = true)
    private String code;

    AdminRole(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }

    @JsonCreator
    public static AdminRole findByCode(String code) {
        return Arrays.stream(AdminRole.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown role {}", code)));
    }
}
