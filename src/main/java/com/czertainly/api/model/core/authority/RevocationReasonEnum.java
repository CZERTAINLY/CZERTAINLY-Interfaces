package com.czertainly.api.model.core.authority;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum RevocationReasonEnum {

    UNSPECIFIED(0),
    KEY_COMPROMISE(1),
    CA_COMPROMISE(2),
    AFFILIATION_CHANGED(3),
    SUPERSEDED(4),
    CESSATION_OF_OPERATION(5),
    CERTIFICATE_HOLD(6),
    REMOVE_FROM_CRL(8),
    PRIVILEGES_WITHDRAWN(9),
    AA_COMPROMISE(10);

    private final int code;

    RevocationReasonEnum(int code) {
        this.code = code;
    }

    public static RevocationReasonEnum fromCode(int code) {
        return Arrays.stream(values())
                .filter(r -> r.code == code)
                .findFirst()
                .orElse(null);
    }

    public int getCode() {
        return code;
    }

    @Override
    @JsonValue
    public String toString() {
        return name();
    }
}
