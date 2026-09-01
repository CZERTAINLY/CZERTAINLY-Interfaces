package com.otilm.api.model.core.settings;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

/**
 * Operator-supplied branding. Every field is optional, and a {@code null} field clears that part of the branding, so a
 * caller must send the full desired state rather than only the fields it wants to change.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrandingSettingsUpdateDto implements Serializable {

    public static final String COLOR_REGEX = "^#[0-9a-fA-F]{6}$";
    public static final String COLOR_VALIDATION_MESSAGE = "Color must be a six-digit hexadecimal value prefixed with '#', for example #0073CF.";

    /**
     * A base64 data URI rather than bare base64, so the media type travels with the payload and a client can hand the
     * value straight to an {@code <img>} element.
     * <p>
     * The payload is matched a quartet at a time rather than as a run of base64 characters with optional padding: only
     * a length that is a multiple of four, with padding solely in the final quartet, is decodable, and {@code "A"},
     * {@code "A="} or {@code "AAAA="} would otherwise pass validation and then fail
     * {@link java.util.Base64.Decoder#decode(String)} in Core. Neither quantifier is nested inside another, so the
     * pattern stays linear on the long inputs a logo produces.
     */
    public static final String LOGO_REGEX = "^data:image/(png|svg\\+xml);base64,"
            + "(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
    public static final String LOGO_VALIDATION_MESSAGE = "Logo must be a base64 data URI with media type image/png or image/svg+xml.";

    /**
     * A logo is limited to one mebibyte of image data. Core enforces the limit exactly, on the decoded bytes, which is
     * where the real cost lies.
     */
    public static final int LOGO_MAX_DECODED_BYTES = 1024 * 1024;

    /**
     * The same one-mebibyte limit expressed on the encoded string, which is all Bean Validation can see: base64 spends
     * four characters per three bytes, plus room for the data URI prefix. A coarse guard that keeps an oversized
     * payload from being decoded at all — Core still checks the exact decoded size, since this bound necessarily rounds
     * up.
     */
    public static final int LOGO_MAX_LENGTH = 32 + 4 * ((LOGO_MAX_DECODED_BYTES + 2) / 3);

    @Schema(description = "Primary brand color, used for the header and for solid interactive elements",
            examples = {"#0073CF"}, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Pattern(regexp = COLOR_REGEX, message = COLOR_VALIDATION_MESSAGE)
    private String primaryColor;

    @Schema(description = "Secondary brand color, used as an accent", examples = {"#00A3E0"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Pattern(regexp = COLOR_REGEX, message = COLOR_VALIDATION_MESSAGE)
    private String secondaryColor;

    @Schema(description = "Page background color of the branded theme", examples = {"#FFFFFF"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Pattern(regexp = COLOR_REGEX, message = COLOR_VALIDATION_MESSAGE)
    private String backgroundColor;

    @Schema(description = "Body text color of the branded theme", examples = {"#171717"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Pattern(regexp = COLOR_REGEX, message = COLOR_VALIDATION_MESSAGE)
    private String textColor;

    @Schema(description = "Logo shown by the branded light theme, as a base64 data URI",
            examples = {"data:image/png;base64,iVBORw0KGgo="}, requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            maxLength = LOGO_MAX_LENGTH)
    @Pattern(regexp = LOGO_REGEX, message = LOGO_VALIDATION_MESSAGE)
    @Size(max = LOGO_MAX_LENGTH)
    private String lightLogo;

    @Schema(description = "Logo shown by the branded dark theme, as a base64 data URI",
            examples = {"data:image/svg+xml;base64,PHN2Zy8+"}, requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            maxLength = LOGO_MAX_LENGTH)
    @Pattern(regexp = LOGO_REGEX, message = LOGO_VALIDATION_MESSAGE)
    @Size(max = LOGO_MAX_LENGTH)
    private String darkLogo;

    @Schema(description = "Branded theme applied when the user has expressed no preference of their own",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private BrandingTheme defaultTheme;

}
