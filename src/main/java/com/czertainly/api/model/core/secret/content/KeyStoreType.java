package com.czertainly.api.model.core.secret.content;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum KeyStoreType implements IPlatformEnum {

    JKS("JKS", "Java Key Store", "A proprietary keystore format used by Java applications."),
    PKCS12("PKCS12", "PKCS #12", "A standard keystore format that can be used across different platforms and applications.")
    ;

    private final String code;
    private final String label;
    private final String description;

    KeyStoreType(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
