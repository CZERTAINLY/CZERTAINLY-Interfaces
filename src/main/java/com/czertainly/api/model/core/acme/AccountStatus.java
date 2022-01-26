package com.czertainly.api.model.core.acme;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

/**
 * Mapping of the ENUM to the possible values of the status of ACME Account
 */
public enum AccountStatus {

    VALID("valid"),
    DEACTIVATED("deactivated"),
    REVOKED("revoked");

    /**
     * Status code for any given status
     */
    private final String code;

    AccountStatus(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return this.code;
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
