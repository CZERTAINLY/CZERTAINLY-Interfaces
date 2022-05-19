package com.czertainly.api.model.core.compliance;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

/*
List if possible status for the individual rules on Compliance checks. This Object will be
used only to define the status of individual rules. This object should not be used to define
the overall compliance status
 */
public enum ComplianceRuleStatus {
    COMPLIANT ("compliant"),
    NON_COMPLIANT ("notCompliant"),
    NOT_APPLICABLE ("notApplicable"),
    ;
    @Schema(description = "Compliance Rule Status",
            example = "compliant", required = true)
    private String code;

    ComplianceRuleStatus(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }

    @JsonCreator
    public static ComplianceRuleStatus findByCode(String code) {
        return Arrays.stream(ComplianceRuleStatus.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown status {}", code)));
    }
}
