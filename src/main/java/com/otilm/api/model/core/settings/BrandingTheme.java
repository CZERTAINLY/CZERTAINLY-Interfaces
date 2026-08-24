package com.otilm.api.model.core.settings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.otilm.api.exception.ValidationError;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.common.enums.IPlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

/**
 * Which of the operator's two branded palettes a client applies when the user has expressed no preference of their own.
 * It selects between the branded variants only; the platform's own light and dark themes are always available
 * regardless of this value.
 */
@Schema(enumAsRef = true)
public enum BrandingTheme implements IPlatformEnum {
    LIGHT("light", "Light", "Branded light theme"),
    DARK("dark", "Dark", "Branded dark theme");

    private static final BrandingTheme[] VALUES;

    static {
        VALUES = values();
    }

    @Schema(description = "Branding theme", examples = {"light"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private final String code;

    @Schema(description = "Name", examples = {"Light"})
    private final String label;

    @Schema(description = "Description", examples = {"Branded light theme"})
    private final String description;

    BrandingTheme(String code, String label, String description) {
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
    public static BrandingTheme findByCode(String code) {
        return Arrays
                .stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new ValidationException(ValidationError.create("Unknown branding theme {}", code)));
    }
}
