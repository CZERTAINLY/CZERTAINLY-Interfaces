package com.czertainly.api.model.core.scep;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FailInfo {
    BAD_ALGORITHM(0),

    BAD_MESSAGE_CHECK(1),

    BAD_REQUEST(2),
    BAD_TIME(3),
    BAD_CERTIFICATE_ID(4);

    private final int value;

    FailInfo(int value) {
        this.value = value;
    }

    public static FailInfo fromValue(String text) {
        for (FailInfo b : FailInfo.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() {
        return String.valueOf(value);
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }
}
