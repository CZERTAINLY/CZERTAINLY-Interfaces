package com.otilm.api.model.common.signature.parameters.pades;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.otilm.api.exception.ValidationError;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.common.enums.IPlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

/**
 * Vertical edge a visible signature anchors to when it is placed by anchor rather than by coordinates.
 */
@Schema(name = "PadesAnchorVerticalAlignment", enumAsRef = true,
        description = "Vertical edge a visible signature anchors to on the page")
public enum VerticalAlignment implements IPlatformEnum {

    TOP(Codes.TOP, "Top", "Anchored to the top edge of the page"),
    MIDDLE(Codes.MIDDLE, "Middle", "Centred vertically on the page"),
    BOTTOM(Codes.BOTTOM, "Bottom", "Anchored to the bottom edge of the page");

    public static class Codes {

        private Codes() {
        }

        public static final String TOP = "top";
        public static final String MIDDLE = "middle";
        public static final String BOTTOM = "bottom";
    }

    private static final VerticalAlignment[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;

    VerticalAlignment(String code, String label, String description) {
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
    public static VerticalAlignment findByCode(String code) {
        return Arrays
                .stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(
                        () -> new ValidationException(ValidationError.create("Unknown vertical alignment {}", code)));
    }
}
