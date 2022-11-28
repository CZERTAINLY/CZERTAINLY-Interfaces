package com.czertainly.api.model.core.certificate;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum CertificateType {
	
    X509("X.509"),
    SSH("SSH")
    ;

    private final String code;

    CertificateType(String string) {
        this.code = string;
    }

    public String getCode() {
        return code;
    }

    public static CertificateType fromCode(String code) {
        return Arrays.stream(values())
                .filter(e -> e.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported type %s.", code)));
    }
}
