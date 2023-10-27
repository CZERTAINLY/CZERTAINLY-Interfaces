package com.czertainly.api.model.core.compliance;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

/*
List if possible status for the Compliance checks. This Object will be used only to
define the status of overall compliance. This object should not be used to define
the compliance status of the individual rules
 */
@Schema(enumAsRef = true)
public enum ComplianceStatus implements IPlatformEnum {
    NOT_CHECKED("not_checked", "Not checked"),
    OK("ok", "Compliant"),
    NOK("nok", "Not Compliant"),
    NA("na", "Not Applicable"),
    ;

    private static final ComplianceStatus[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;

    ComplianceStatus(String code, String label) {
        this(code, label,null);
    }

    ComplianceStatus(String code, String label, String description) {
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
    public static ComplianceStatus findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown Compliance status {}", code)));
    }
}
