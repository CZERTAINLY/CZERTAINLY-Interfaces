package com.czertainly.api.model.core.scep;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PkiStatus {
    SUCCESS(0),

    FAILURE(2),

    PENDING(3);

    private final int value;

    PkiStatus(int value) {
        this.value = value;
    }

    public static PkiStatus fromValue(String text) {
        for (PkiStatus b : PkiStatus.values()) {
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
