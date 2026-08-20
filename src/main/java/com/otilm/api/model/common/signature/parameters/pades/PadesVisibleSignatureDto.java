package com.otilm.api.model.common.signature.parameters.pades;

import com.otilm.api.model.common.signature.SignatureParameterGroup;
import com.otilm.api.model.common.signature.parameters.RequestParameterGroup;
import com.otilm.api.model.common.signature.parameters.pades.validation.ImageAndMimeTypeTogether;
import com.otilm.api.model.common.validation.NullableNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * What a PAdES visible signature shows, and where it goes.
 *
 * <p>
 * The image travels as resolved bytes, so the connector stays stateless. A stamp from the profile is indistinguishable
 * from one sent with the request.
 * </p>
 */
@Getter
@Setter
@ToString
@ImageAndMimeTypeTogether
@Schema(name = "PadesVisibleSignature",
        description = "Content of a PAdES visible signature. A stamp image and its media type travel together: one "
                + "without the other is rejected. Appearance styling is operator-only, configured on the Signature "
                + "Formatting Provider rather than here. That covers colours, padding, fonts, scaling and caption "
                + "alignment.")
public class PadesVisibleSignatureDto {

    @RequestParameterGroup(SignatureParameterGroup.VISIBLE_SIGNATURE_CONTENT)
    @Schema(description = "Whether the signature is drawn on the page at all. Absent is unspecified: on a request "
            + "the Signing Profile's default decides, and in the effective parameters the connector applies its own "
            + "default.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "true")
    private Boolean visible;

    @ToString.Exclude
    @RequestParameterGroup(SignatureParameterGroup.VISIBLE_SIGNATURE_CONTENT)
    @Size(max = 4096, message = "text must be at most 4096 characters")
    @NullableNotBlank(message = "text must not be blank if provided")
    @Schema(description = "Caption drawn in the signature. Plain text: any transport encoding belongs to the door "
            + "that used it, not to this contract.", requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            example = "Signed by Jane Doe")
    private String text;

    @ToString.Exclude
    @RequestParameterGroup(SignatureParameterGroup.VISIBLE_SIGNATURE_CONTENT)
    @Size(max = 262144, message = "image must be at most 262144 bytes")
    @Schema(description = "Stamp image drawn in the signature, base64-encoded in JSON. At most 256 KiB decoded. The "
            + "maxLength below counts base64 characters, so it is the stricter of the two caps. Always resolved "
            + "bytes: this contract carries no reference, no identifier and no path.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private byte[] image;

    @RequestParameterGroup(SignatureParameterGroup.VISIBLE_SIGNATURE_CONTENT)
    @Pattern(regexp = "image/png|image/jpeg", message = "imageMimeType must be image/png or image/jpeg")
    @Schema(description = "Media type of the stamp image. PNG and JPEG only; SVG is excluded because the renderer "
            + "wants a raster. Matched exactly: image/png or image/jpeg, lower-case, with no parameters.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, allowableValues = {"image/png", "image/jpeg"},
            example = "image/png")
    private String imageMimeType;

    @Valid
    @Schema(description = "Where the signature lands on the page", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private PadesVisibleSignaturePlacementDto placement;
}
