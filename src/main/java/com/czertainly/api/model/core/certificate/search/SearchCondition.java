package com.czertainly.api.model.core.certificate.search;

import java.util.Arrays;

public enum SearchCondition {
    EQUALS("="),
    NOT_EQUALS("<>"),
    GREATER(">"),
    LESSER("<"),
    CONTAINS("LIKE")
    ;

    private final String code;

    SearchCondition(String string) {
        this.code = string;
    }

    public String getCode() {
        return code;
    }

    public static SearchCondition fromCode(String code) {
        return Arrays.stream(values())
                .filter(e -> e.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported type %s.", code)));
    }
}
