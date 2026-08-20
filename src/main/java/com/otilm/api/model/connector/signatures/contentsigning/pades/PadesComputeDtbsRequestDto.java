package com.otilm.api.model.connector.signatures.contentsigning.pades;

import com.otilm.api.model.common.signature.SignatureFamily;
import com.otilm.api.model.common.signature.parameters.pades.PadesSignatureParametersDto;
import com.otilm.api.model.connector.signatures.contentsigning.common.ComputeDtbsRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * PAdES variant of the {@code computeDtbs} request.
 *
 * <p>
 * PAdES is always enveloped, so there is no packaging choice to express. Nor does the digest-only document transfer
 * arrive here: laying out an incremental revision needs the document itself.
 * </p>
 *
 * <p>
 * The refusal is deliberate. A silently dropped parameter would produce a signature that does not say what the caller
 * asked for.
 * </p>
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(title = "PadesComputeDtbsRequestDto", description = "computeDtbs request for the PAdES family")
public class PadesComputeDtbsRequestDto extends ComputeDtbsRequestDto {

    @Valid
    @Schema(description = "The effective signature parameters. Already merged over the Signing Profile's defaults, "
            + "already checked against the profile's allowed parameter groups, and with the stamp image already "
            + "resolved to bytes. The connector applies these values and never authorizes them. A connector that "
            + "cannot honour a parameter it is sent answers 422 with errorCode PARAMETER_UNSUPPORTED. Absent when the "
            + "profile sets no defaults and the request supplies nothing.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private PadesSignatureParametersDto signatureParameters;

    public PadesComputeDtbsRequestDto() {
        super(SignatureFamily.PADES);
    }
}
