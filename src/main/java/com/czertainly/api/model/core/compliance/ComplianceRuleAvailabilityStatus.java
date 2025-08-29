package com.czertainly.api.model.core.compliance;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

/*
List of possible status for rule compared to existing association in compliance profile. It indicates to user if rule needs to be replaced/removed or changed.
 */

@Schema(enumAsRef = true)
public enum ComplianceRuleAvailabilityStatus implements IPlatformEnum {
    AVAILABLE("available", "Available"),
    NOT_AVAILABLE("not_available", "Not available"),
    UPDATED("updated", "Updated");

    private static final ComplianceRuleAvailabilityStatus[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;

    ComplianceRuleAvailabilityStatus(String code, String label) {
        this(code, label, null);
    }

    ComplianceRuleAvailabilityStatus(String code, String label, String description) {
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
    public static ComplianceRuleAvailabilityStatus findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown Compliance rule availability status {}", code)));
    }
}
