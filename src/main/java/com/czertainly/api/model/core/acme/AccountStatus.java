package com.czertainly.api.model.core.acme;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

/**
 * Mapping of the ENUM to the possible values of the status of ACME Account
 */
@Schema(enumAsRef = true)
public enum AccountStatus implements IPlatformEnum {

    VALID("valid", "Valid"),
    DEACTIVATED("deactivated", "Deactivated"),
    REVOKED("revoked", "Revoked");

    /**
     * Status code for any given status
     */
    private final String code;
    private final String label;
    private final String description;

    AccountStatus(String code, String label) {
        this(code, label,null);
    }

    AccountStatus(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @JsonCreator
    public static AccountStatus findByCode(String code) {
        return Arrays.stream(AccountStatus.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown ACME Account status code {}", code)));
    }
}
