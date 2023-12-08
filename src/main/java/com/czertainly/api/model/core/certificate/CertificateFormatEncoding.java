package com.czertainly.api.model.core.certificate;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;


public enum CertificateFormatEncoding implements IPlatformEnum {

    PEM("pem", "PEM", "PEM certificate format encoding"),
    DER("der", "DER", "DER certificate format encoding");


    private final String code;
    private final String label;
    private final String description;

    private static final CertificateFormatEncoding[] VALUES;

    static {
        VALUES = values();
    }

    CertificateFormatEncoding(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    @JsonCreator
    public static CertificateFormatEncoding fromCode(String code) {
        return Arrays.stream(VALUES)
                .filter(e -> e.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported certificate format encoding %s.", code)));
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
}
