package com.otilm.api.model.core.branding;

import com.otilm.api.model.core.settings.BrandingTheme;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

/**
 * The only branding an unauthenticated caller may read.
 * <p>
 * Deliberately a standalone type rather than a view of {@code BrandingSettingsDto}: extending or embedding a settings
 * DTO would mean every field later added to platform settings is served to anonymous callers by default. Fields are
 * duplicated here so that widening this response is always an explicit act.
 * <p>
 * Nothing is omitted on {@code null}. The response has a fixed shape whether or not branding is configured, so a client
 * can read it once at startup without having to distinguish "absent" from "not yet loaded".
 */
@Data
public class PublicBrandingDto implements Serializable {

    @Schema(description = "Whether the operator has configured branding. When false, every other field is null and a "
            + "client applies the platform's own themes.", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean configured;

    @Schema(description = "Primary brand color, used for the header and for solid interactive elements",
            examples = {"#0073CF"}, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String primaryColor;

    @Schema(description = "Secondary brand color, used as an accent", examples = {"#00A3E0"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String secondaryColor;

    @Schema(description = "Tertiary brand color, used as an additional accent", examples = {"#7B61FF"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String tertiaryColor;

    @Schema(description = "Page background color of the branded theme", examples = {"#FFFFFF"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String backgroundColor;

    @Schema(description = "Body text color of the branded theme", examples = {"#171717"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String textColor;

    @Schema(description = "Logo shown by the branded light theme, as a base64 data URI",
            examples = {"data:image/png;base64,iVBORw0KGgo="}, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String lightLogo;

    @Schema(description = "Logo shown by the branded dark theme, as a base64 data URI",
            examples = {"data:image/svg+xml;base64,PHN2Zy8+"}, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String darkLogo;

    @Schema(description = "Branded theme applied when the user has expressed no preference of their own",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private BrandingTheme defaultTheme;

}
