package com.czertainly.api.model.core.settings;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

public enum SettingsSection implements IPlatformEnum {
    PLATFORM("platform", "Platform", "CZERTAINLY platform settings"),
    NOTIFICATIONS("notifications", "Notifications", "CZERTAINLY notifications settings"),
    OAUTH2_PROVIDER("oauth2Provider", "OAuth2 Provider", "Provider for OAuth2 Authentication")
    ;

    private static final SettingsSection[] VALUES;

    static {
        VALUES = values();
    }

    @Schema(
            description = "Setting section",
            example = "platform",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private final String code;

    @Schema(
            description = "Name",
            example = "Platform"
    )
    private final String label;

    @Schema(
            description = "Description",
            example = "CZERTAINLY platform settings"
    )
    private final String description;

    SettingsSection(String code, String label) {
        this(code, label,null);
    }

    SettingsSection(String code, String label, String description) {
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
    public static SettingsSection findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown settings section {}", code)));
    }
}
