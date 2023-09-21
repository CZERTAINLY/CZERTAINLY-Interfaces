package com.czertainly.api.model.core.certificate;

import com.czertainly.api.model.common.enums.IPlatformEnum;

public enum CertificateFormat implements IPlatformEnum {

    PKCS7("pkcs7","PKCS#7", "PKCS#7 certificate format"),
    PEM("pem", "PEM","PKCS#7 certificate format");

    private final String code;
    private final String label;
    private final String description;

    CertificateFormat(String code, String label, String description) {
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
}
