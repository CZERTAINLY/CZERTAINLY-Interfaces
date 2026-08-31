package com.otilm.api.model.connector.signatures.contentsigning.common;

import com.otilm.api.model.common.enums.cryptography.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Request to embed the signature value the platform's signer produced, completing a SIGNED-level signature. A connector
 * carries {@code signatureAlgorithm} inside its {@code formattingContext} and refuses an embed that disagrees with it;
 * the {@code embedSignatureValue} operation description states that duty and the error code it answers with.
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(name = "EmbedSignatureValueRequest",
        description = "Request to embed a signature value into the structure computeDtbs prepared")
public class EmbedSignatureValueRequestDto extends ContentSigningFormattingRequestDto {

    @ToString.Exclude
    @NotNull(message = "signatureValue is required")
    @Schema(description = "Raw signature over the data-to-be-signed bytes, base64-encoded in JSON",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private byte[] signatureValue;

    @NotNull(message = "signatureAlgorithm is required")
    @Schema(description = "Signature algorithm the signature value was produced with, the same value computeDtbs was "
            + "given. Set by the platform from the resolved signer; a connector MUST NOT select an algorithm itself, "
            + "and MUST refuse a value that disagrees with the algorithm its formattingContext committed to with 422 "
            + "and errorCode CONTEXT_MISMATCH.", requiredMode = Schema.RequiredMode.REQUIRED)
    private SignatureAlgorithm signatureAlgorithm;

    @ToString.Exclude
    @NotNull(message = "formattingContext is required")
    @Schema(description = "The formattingContext returned by computeDtbs, replayed verbatim. Opaque to the "
            + "platform, which never inspects, rewrites or stores it.", requiredMode = Schema.RequiredMode.REQUIRED)
    private byte[] formattingContext;
}
