package com.czertainly.api.model.core.notification;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum RecipientType implements IPlatformEnum {

    USER("user", "User"),
    GROUP("group", "Group"),
    ROLE("role", "Role"),
    OWNER("owner", "Owner");

    private static final RecipientType[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;

    RecipientType(String code, String label) {
        this(code, label,null);
    }

    RecipientType(String code, String label, String description) {
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
    public static RecipientType findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(a -> a.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown recipient type code {}", code)));
    }
}

