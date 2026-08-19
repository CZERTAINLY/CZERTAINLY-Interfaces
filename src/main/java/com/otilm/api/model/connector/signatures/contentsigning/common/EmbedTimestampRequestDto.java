package com.otilm.api.model.connector.signatures.contentsigning.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Request to embed a timestamp token the platform obtained, shared by the signature timestamp and the archive
 * timestamp.
 *
 * <p>
 * The token is always issued platform-side, against the timestamping profile the signing profile references, so the
 * connector never contacts a timestamp authority itself.
 * </p>
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(name = "EmbedTimestampRequest", description = "Request to embed a timestamp token into a signed document")
public class EmbedTimestampRequestDto extends SignedDocumentRequestDto {

    @ToString.Exclude
    @NotNull(message = "timestampToken is required")
    @Schema(description = "DER-encoded RFC 3161 timestamp token covering the imprint the matching compute "
            + "operation returned, base64-encoded in JSON", requiredMode = Schema.RequiredMode.REQUIRED)
    private byte[] timestampToken;
}
