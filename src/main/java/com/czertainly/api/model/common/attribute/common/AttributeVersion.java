package com.czertainly.api.model.common.attribute.common;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum AttributeVersion implements IPlatformEnum {

    V2(Codes.V2),
    V3(Codes.V3);

    private final String code;

    AttributeVersion(String code) {
        this.code = code;
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    public static class Codes {
        public static final String V2 = "v2";
        public static final String V3 = "v3";
    }
}
