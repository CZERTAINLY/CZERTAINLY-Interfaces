package com.czertainly.api.model.core.enums;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum CertificateRequestFormat implements IPlatformEnum {

    PKCS10("pkcs10", "PKCS#10");

    private String code;

    private String label;

    private String description;

    CertificateRequestFormat(String code, String label) {
        this.code = code;
        this.label = label;
    }

    CertificateRequestFormat(String code, String label, String description) {
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
    public static CertificateRequestFormat fromCode(final String code) {
        return Arrays.stream(values())
                .filter(e -> e.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported certificate request format %s.", code)));
    }
}
