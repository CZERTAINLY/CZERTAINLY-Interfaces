package com.otilm.api.model.connector.signatures.contentsigning.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.otilm.api.model.common.signature.SignatureLevel;
import com.otilm.api.model.connector.common.v2.OperationExecutionMode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Request to raise a signature to a higher level by fetching the validation material it needs and embedding it in one
 * call.
 *
 * <p>
 * This is the one operation exempt from the determinism rule: it fetches, so a retry refetches rather than replaying,
 * and a repeat call may legitimately embed different material. It runs on the deployment allowed network egress.
 * </p>
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(name = "ExtendToLevelRequest",
        description = "Request to fetch and embed validation material, raising a signature to the target level")
public class ExtendToLevelRequestDto extends SignedDocumentRequestDto {

    @NotNull(message = "targetLevel is required")
    @Schema(description = "Level the signature is to reach. LONG_TERM is the only target this operation serves; the "
            + "timestamped levels are reached through their own imprint and embed pair instead.",
            requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {SignatureLevel.Codes.LONG_TERM})
    private SignatureLevel targetLevel;

    /** The schema restricts the value; this rejects it at runtime too, so both halves of the contract agree. */
    @JsonIgnore
    @Schema(hidden = true)
    @AssertTrue(message = "targetLevel must be LONG_TERM: this operation serves no other level")
    public boolean isTargetLevelServedByThisOperation() {
        return targetLevel == null || targetLevel == SignatureLevel.LONG_TERM;
    }

    @Valid
    @NotNull(message = "certificateChain is required")
    @Schema(description = "The chain to build validation material for, with the designation of where it stops",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private CertificateChainDto certificateChain;

    @Valid
    @Schema(description = "Validation material already obtained elsewhere. When supplied, the connector embeds it and "
            + "fetches only what is still missing. Reserved for a deployment that sources material outside the "
            + "connector; no platform caller fills it today.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private ValidationMaterialDto prefetchedMaterial;

    @NotNull(message = "executionMode is required")
    @Schema(description = "How the caller wants the operation executed. The platform always sends SYNCHRONOUS and "
            + "treats a 202 as a contract violation; ASYNCHRONOUS opens once both sides advertise support for it.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private OperationExecutionMode executionMode;
}
