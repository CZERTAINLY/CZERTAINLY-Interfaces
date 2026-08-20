package com.otilm.api.model.common.signature.parameters.pades;

import com.otilm.api.model.common.signature.SignatureFamily;
import com.otilm.api.model.common.signature.SignatureParameterGroup;
import com.otilm.api.model.common.signature.parameters.RequestParameterGroup;
import com.otilm.api.model.common.signature.parameters.SignatureParametersDto;
import com.otilm.api.model.common.validation.NullableNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The PAdES signature parameters a caller may influence, each gated by the group its field declares.
 *
 * <p>
 * Appearance styling is absent by design. It is brand identity, correct whichever document arrives, so it stays an
 * operator-only attribute on the Signature Formatting Provider.
 * </p>
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(name = "PadesSignatureParameters",
        description = "Signature parameters for the PAdES family. Every parameter is optional and independent. The "
                + "field that declares this object says which values it carries and how absence is resolved.")
public class PadesSignatureParametersDto extends SignatureParametersDto {

    @ToString.Exclude
    @RequestParameterGroup(SignatureParameterGroup.SIGNATURE_CONTEXT)
    @Size(max = 512, message = "reason must be at most 512 characters")
    @NullableNotBlank(message = "reason must not be blank if provided")
    @Schema(description = "Why the document was signed, recorded as the /Reason entry of the signature dictionary",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Contract approval")
    private String reason;

    @ToString.Exclude
    @RequestParameterGroup(SignatureParameterGroup.SIGNATURE_CONTEXT)
    @Size(max = 512, message = "location must be at most 512 characters")
    @NullableNotBlank(message = "location must not be blank if provided")
    @Schema(description = "Where the document was signed, recorded as the /Location entry of the signature dictionary",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Prague")
    private String location;

    @ToString.Exclude
    @RequestParameterGroup(SignatureParameterGroup.SIGNATURE_CONTEXT)
    @Size(max = 512, message = "contactInfo must be at most 512 characters")
    @NullableNotBlank(message = "contactInfo must not be blank if provided")
    @Schema(description = "How to reach the signer, recorded as the /ContactInfo entry of the signature dictionary",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "jane.doe@example.com")
    private String contactInfo;

    @ToString.Exclude
    @RequestParameterGroup(SignatureParameterGroup.SIGNER_IDENTITY)
    @Size(max = 512, message = "signerName must be at most 512 characters")
    @NullableNotBlank(message = "signerName must not be blank if provided")
    @Schema(description = "Name shown as the signer, recorded as the /Name entry of the signature dictionary. Absent "
            + "means the name is derived from the signing certificate.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Jane Doe")
    private String signerName;

    @RequestParameterGroup(SignatureParameterGroup.SIGNATURE_SCOPE)
    @Schema(description = "Whether the signature certifies the document or approves a revision of it",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private PadesSignatureScope signatureScope;

    @RequestParameterGroup(SignatureParameterGroup.SIGNED_ATTRIBUTES)
    @Schema(description = "Commitment the signer makes, bound into the signature as a signed attribute",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private CommitmentType commitmentType;

    @ToString.Exclude
    @RequestParameterGroup(SignatureParameterGroup.SIGNED_ATTRIBUTES)
    @Size(max = 10, message = "claimedRoles must contain at most 10 roles")
    @Schema(description = "Roles the signer claims, bound into the signature as a signed attribute. Claimed only: a "
            + "certified role would be an attribute certificate, which this contract does not carry.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "[\"Head of Legal\"]")
    private List<@NotNull(message = "claimedRoles must not contain null items") @Size(max = 256,
            message = "each claimed role must be at most 256 characters") @NullableNotBlank(
                    message = "each claimed role must not be blank") String> claimedRoles;

    @Valid
    @Schema(description = "The visible signature drawn on the page, and where it goes",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private PadesVisibleSignatureDto visibleSignature;

    public PadesSignatureParametersDto() {
        super(SignatureFamily.PADES);
    }
}
