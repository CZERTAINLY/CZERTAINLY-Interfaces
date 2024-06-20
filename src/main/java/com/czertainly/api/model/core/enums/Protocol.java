package com.czertainly.api.model.core.enums;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Protocol implements IPlatformEnum {

    ACME("acme", "ACME Protocol"),
    SCEP("scep", "SCEP Protocol"),
    CMP("cmp", "CMP Protocol")
    ;

    private final String code;
    private final String label;

   Protocol(String code, String label) {
        this.code = code;
        this.label = label;
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
        return null;
    }
}
