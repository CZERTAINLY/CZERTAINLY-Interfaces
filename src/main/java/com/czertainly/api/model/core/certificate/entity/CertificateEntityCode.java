package com.czertainly.api.model.core.certificate.entity;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.core.certificate.CertificateStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

public enum CertificateEntityCode {

    SERVER("server"),
    ROUTER("router"),
    HSM("HSM"),
    SWITCH("switch"),
    LOAD_BALANCER("loadBalancer"),
    FIREWALL("firewall"),
    MDM("MDM"),
    CLOUD("cloud")
    ;

    @Schema(description = "Entity Type",
            example = "router", required = true)
    private String code;

    CertificateEntityCode(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }

    @JsonCreator
    public static CertificateEntityCode findByCode(String code) {
        return Arrays.stream(CertificateEntityCode.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown entity {}", code)));
    }
}
