package com.czertainly.api.model.core.setting;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum Section {
    GENERAL("general", "General", "General settings");
    @Schema(
            description = "Setting section",
            example = "general",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private final String code;

    @Schema(
            description = "Name",
            example = "Util Service"
    )
    private final String name;

    @Schema(
            description = "Description",
            example = "Util Service connection details"
    )
    private final String description;

    Section(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() { return this.description; }

    @JsonCreator
    public static Section findByCode(String code) {
        return Arrays.stream(Section.values())
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown code {}", code)));
    }
}
