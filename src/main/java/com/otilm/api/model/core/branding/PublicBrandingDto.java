package com.otilm.api.model.core.branding;

import com.fasterxml.jackson.annotation.JsonInclude;
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
 * {@code ALWAYS} rather than the inherited default: Core's web {@code ObjectMapper} serializes with {@code NON_NULL},
 * which would drop every unset field here. A client reads this before it has any session, so the response keeps a fixed
 * shape whether or not branding is configured — "no logo configured" and "response not understood" must not look alike.
 * <p>
 * The colour and logo fields are therefore {@code REQUIRED} and typed {@code ["string", "null"]}: a key that is always
 * present but may carry {@code null} is exactly that, and declaring it optional-and-non-nullable instead would leave a
 * generated client unable to represent the unconfigured response it will receive first.
 * <p>
 * {@code defaultTheme} is the one field that keeps the omit-when-null treatment. It resolves to a {@code $ref} on the
 * shared {@code BrandingTheme} schema, and neither swagger-core nor springdoc can attach nullability to a {@code $ref}
 * without emitting a sibling {@code type} that contradicts the referenced enum. Absent-when-unset is representable in
 * every generated client; {@code null} on a required {@code $ref} is representable in none, so absence wins.
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class PublicBrandingDto implements Serializable {

    @Schema(description = "Whether the operator has configured branding. When false, every colour and logo field is "
            + "null, defaultTheme is absent, and a client applies the platform's own themes.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean configured;

    @Schema(description = "Primary brand color, used for the header and for solid interactive elements",
            examples = {"#0073CF"}, requiredMode = Schema.RequiredMode.REQUIRED, types = {"string", "null"})
    private String primaryColor;

    @Schema(description = "Secondary brand color, used as an accent", examples = {"#00A3E0"},
            requiredMode = Schema.RequiredMode.REQUIRED, types = {"string", "null"})
    private String secondaryColor;

    @Schema(description = "Page background color of the branded theme", examples = {"#FFFFFF"},
            requiredMode = Schema.RequiredMode.REQUIRED, types = {"string", "null"})
    private String backgroundColor;

    @Schema(description = "Body text color of the branded theme", examples = {"#171717"},
            requiredMode = Schema.RequiredMode.REQUIRED, types = {"string", "null"})
    private String textColor;

    @Schema(description = "Logo shown by the branded light theme, as a base64 data URI",
            examples = {"data:image/png;base64,iVBORw0KGgo="}, requiredMode = Schema.RequiredMode.REQUIRED,
            types = {"string", "null"})
    private String lightLogo;

    @Schema(description = "Logo shown by the branded dark theme, as a base64 data URI",
            examples = {"data:image/svg+xml;base64,PHN2Zy8+"}, requiredMode = Schema.RequiredMode.REQUIRED,
            types = {"string", "null"})
    private String darkLogo;

    /** Omitted rather than sent as {@code null} when branding is unconfigured — see the class documentation. */
    @Schema(description = "Branded theme applied when the user has expressed no preference of their own. Absent when "
            + "branding is not configured.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BrandingTheme defaultTheme;

}
