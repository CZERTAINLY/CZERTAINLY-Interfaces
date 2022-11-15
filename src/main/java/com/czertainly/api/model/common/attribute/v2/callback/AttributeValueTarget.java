package com.czertainly.api.model.common.attribute.v2.callback;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema( enumAsRef = true)
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
