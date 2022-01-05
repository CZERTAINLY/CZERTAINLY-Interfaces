package com.czertainly.api.model.core.certificate;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

public enum CertificateValidationStatus {
    SUCCESS("success"),
    FAILED("failed"),
    WARNING("warning"),
    REVOKED("revoked"),
    NOT_CHECKED("not_checked"),
    INVALID("invalid"),
    EXPIRING("expiring"),
    EXPIRED("expired");

    @Schema(description = "Certificate Status",
            example = "valid", required = true)
    private String code;

    CertificateValidationStatus(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }

    @JsonCreator
    public static CertificateValidationStatus findByCode(String code) {
        return Arrays.stream(CertificateValidationStatus.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown status {}", code)));
    }


}
