package com.otilm.api.model.common.signature.parameters.pades;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.otilm.api.exception.ValidationError;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.common.enums.IPlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

/**
 * Horizontal edge a visible signature anchors to when it is placed by anchor rather than by coordinates.
 */
@Schema(name = "PadesAnchorHorizontalAlignment", enumAsRef = true,
        description = "Horizontal edge a visible signature anchors to on the page")
public enum HorizontalAlignment implements IPlatformEnum {

    LEFT(Codes.LEFT, "Left", "Anchored to the left edge of the page"),
    CENTER(Codes.CENTER, "Center", "Centred horizontally on the page"),
    RIGHT(Codes.RIGHT, "Right", "Anchored to the right edge of the page");

    public static class Codes {

        private Codes() {
        }

        public static final String LEFT = "left";
        public static final String CENTER = "center";
        public static final String RIGHT = "right";
    }

    private static final HorizontalAlignment[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;

    HorizontalAlignment(String code, String label, String description) {
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
    public static HorizontalAlignment findByCode(String code) {
        return Arrays
                .stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(
                        () -> new ValidationException(ValidationError.create("Unknown horizontal alignment {}", code)));
    }
}
