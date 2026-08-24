package com.otilm.api.model.connector.signatures.contentsigning.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Request to embed the signature value the platform's signer produced, completing a SIGNED-level signature.
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

    @ToString.Exclude
    @NotNull(message = "formattingContext is required")
    @Schema(description = "The formattingContext returned by computeDtbs, replayed verbatim. Opaque to the "
            + "platform, which never inspects, rewrites or stores it.", requiredMode = Schema.RequiredMode.REQUIRED)
    private byte[] formattingContext;
}
