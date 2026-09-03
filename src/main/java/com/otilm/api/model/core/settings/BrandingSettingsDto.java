package com.otilm.api.model.core.settings;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

/**
 * Branding as stored by the platform. Unset parts are omitted rather than sent as {@code null}, which keeps the
 * response small when no logo has been uploaded.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrandingSettingsDto implements Serializable {

    @Schema(description = "Primary brand color, used for the header and for solid interactive elements",
            examples = {"#0073CF"}, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String primaryColor;

    @Schema(description = "Secondary brand color, used as an accent", examples = {"#00A3E0"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String secondaryColor;

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
