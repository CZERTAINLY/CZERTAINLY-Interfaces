package com.czertainly.api.model.common.attribute.common;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(enumAsRef = true)
public enum AttributeVersion implements IPlatformEnum {

    V2(Codes.V2, 2),
    V3(Codes.V3, 3);

    private final String code;

    @Getter
    private final int version;

    AttributeVersion(String code, int version) {
        this.code = code;
        this.version = version;
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
        private Codes(){}
        public static final String V2 = "v2";
        public static final String V3 = "v3";
    }
}
