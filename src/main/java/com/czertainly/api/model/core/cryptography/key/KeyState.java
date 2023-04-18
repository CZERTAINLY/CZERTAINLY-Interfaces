package com.czertainly.api.model.core.cryptography.key;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.czertainly.api.model.connector.cryptography.enums.IAbstractSearchableEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum KeyState  implements IPlatformEnum, IAbstractSearchableEnum {
    PRE_ACTIVE("pre-active", "Pre-active"),
    ACTIVE("active", "Active"),
    DEACTIVATED("deactivated", "Deactivated"),
    COMPROMISED("compromised", "Compromised"),
    DESTROYED("destroyed", "Destroyed"),
    COMPROMISED_DESTROYED("compromisedDestroyed", "Compromised Destroyed");

    @Schema(description = "State of the key",
            example = "active", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String code;
    private final String label;
    private final String description;

    KeyState(String code, String label) {
        this(code, label,null);
    }

    KeyState(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    @JsonCreator
    public static KeyState findByCode(String code) {
        return Arrays.stream(KeyState.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown code {}", code)));
    }

    @Override
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

    @Override
    public String getEnumLabel() {
        return code;
    }
}
