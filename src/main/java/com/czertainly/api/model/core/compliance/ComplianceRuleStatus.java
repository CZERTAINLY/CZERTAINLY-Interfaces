package com.czertainly.api.model.core.compliance;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

/*
List if possible status for the individual rules on Compliance checks. This Object will be
used only to define the status of individual rules. This object should not be used to define
the overall compliance status
 */

@Schema(enumAsRef = true)
public enum ComplianceRuleStatus implements IPlatformEnum {
    OK("ok", "Compliant"),
    NOK("nok", "Not Compliant"),
    NA("na", "Not Applicable"),
    ;

    private static final ComplianceRuleStatus[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;

    ComplianceRuleStatus(String code, String label) {
        this(code, label,null);
    }

    ComplianceRuleStatus(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    @Override
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
    public static ComplianceRuleStatus findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown Compliance rule status {}", code)));
    }
}
