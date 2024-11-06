package com.czertainly.api.model.core.logging.enums;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ActorType implements IPlatformEnum {
    ANONYMOUS("anonymous", "Anonymous"),
    CORE("core", "Core"),
    USER("user", "User"),
    CONNECTOR("connector", "Connector"),
    PROTOCOL("protocol", "Protocol");

    private static final ActorType[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;

    ActorType(String code, String label) {
        this(code, label,null);
    }

    ActorType(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
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
        return this.description;
    }

    @JsonCreator
    public static ActorType findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(a -> a.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown actor type code {}", code)));
    }
}
