package com.czertainly.api.model.client.connector.v2;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;


@Schema(enumAsRef = true)
public enum CommonFeatureFlag implements FeatureFlag {

    STATELESS("stateless", "Stateless Connector", "A stateless connector does not require persistence layer (e.g. database)");

    private static final CommonFeatureFlag[] VALUES;

    static {
        VALUES = values();
    }

    @Schema(description = "Feature flag code",
            examples = {"stateless"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private final String code;
    private final String label;
    private final String description;

    CommonFeatureFlag(String code, String label) {
        this(code, label,null);
    }

    CommonFeatureFlag(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
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

    @JsonCreator
    public static CommonFeatureFlag findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown common feature flag code {}", code)));
    }

    @Override
    public String toString() {
        return this.code;
    }
}
