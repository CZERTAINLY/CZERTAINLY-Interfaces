package com.czertainly.api.model.raprofile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RegistrationStatus {
    NEW("NEW"),

    ANY("ANY");

    private final String value;

    RegistrationStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static RegistrationStatus fromValue(String text) {
        for (RegistrationStatus b : RegistrationStatus.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }
}
