package com.czertainly.api.model.common.attribute.constraint;

import java.util.Arrays;

public enum AttributeConstraintType {
    REGEXP("regExp"),
    RANGE("range"),
    DATETIME("dateTime");

    private final String code;

    AttributeConstraintType(String string) {
        this.code = string;
    }

    public String getCode() {
        return code;
    }

    public static AttributeConstraintType fromCode(String code) {
        return Arrays.stream(values())
                .filter(e -> e.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported type %s.", code)));
    }

}
