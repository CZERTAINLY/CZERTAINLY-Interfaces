package com.czertainly.api.model.common;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.core.discovery.DiscoveryStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

public enum AttributeValueTarget {

    PATH_VARIABLE("pathVariable"),
    REQUEST_PARAMETER("requestParameter"),
    BODY("body");
    @Schema(description = "Attribute value Target",
            example = "pathVariable", required = true)
    private String code;

    AttributeValueTarget(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }

    @JsonCreator
    public static AttributeValueTarget findByCode(String code) {
        return Arrays.stream(AttributeValueTarget.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown Target {}", code)));
    }
}
