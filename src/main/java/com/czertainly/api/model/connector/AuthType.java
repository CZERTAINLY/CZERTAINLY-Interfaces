package com.czertainly.api.model.connector;

import java.util.Arrays;

public enum AuthType {
    NONE("none"),
    BASIC("basic"),
    CERTIFICATE("certificate"),
    API_KEY("apiKey"),
    JWT("jwt");

    private String code;

    AuthType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static AuthType findByCode(String code) {
        return Arrays.stream(values())
                .filter(a -> a.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown code " + code));
    }
}
