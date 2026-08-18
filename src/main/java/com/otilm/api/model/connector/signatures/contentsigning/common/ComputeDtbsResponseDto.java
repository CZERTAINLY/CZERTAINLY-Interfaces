package com.otilm.api.model.connector.signatures.contentsigning.common;

import com.otilm.api.model.common.enums.cryptography.DigestAlgorithm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Result of {@code computeDtbs}: the bytes to sign, the digest of the document they commit to, and the connector's own
 * working state to hand back when the signature value comes home.
 */
@Getter
@Setter
@ToString
@Schema(name = "ComputeDtbsResponse",
        description = "Data-to-be-signed bytes, the documentDigest they commit to, and the formattingContext that "
                + "pairs with them")
public class ComputeDtbsResponseDto {

    @ToString.Exclude
    @NotNull(message = "dtbs is required")
    @Schema(description = "The data-to-be-signed bytes, base64-encoded in JSON. The platform verifies that these "
            + "bytes embed the digest of the document the user authorized before signing them.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private byte[] dtbs;

    @ToString.Exclude
    @NotNull(message = "documentDigest is required")
    @Schema(description = "Digest of the document the connector bound into dtbs, base64-encoded in JSON. The platform "
            + "compares it for equality against the digest the user authorized and releases the signing key only on a "
            + "match, so a connector MUST echo the digest it actually committed to rather than recomputing an "
            + "expected one.", requiredMode = Schema.RequiredMode.REQUIRED)
    private byte[] documentDigest;

    @NotNull(message = "documentDigestAlgorithm is required")
    @Schema(description = "Algorithm that produced documentDigest. A digest carries no meaning without it, and the "
            + "platform needs it to compare against the authorized digest. The enum spans every algorithm the platform "
            + "knows, including collision-broken ones: the platform rejects any algorithm outside the Signing "
            + "Profile's allowedDigestAlgorithms before it compares, so a connector never has to police this itself.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private DigestAlgorithm documentDigestAlgorithm;

    @ToString.Exclude
    @NotNull(message = "formattingContext is required")
    @Schema(description = "Whatever the connector needs to complete this signature once the signature value comes "
            + "back, base64-encoded in JSON. The platform treats it as opaque bytes: it is replayed verbatim to "
            + "embedSignatureValue and never stored, because every intermediate is reproducible by replaying the "
            + "operation sequence. It counts toward the request size ceiling, so a connector should keep it no "
            + "larger than the work actually requires.", requiredMode = Schema.RequiredMode.REQUIRED)
    private byte[] formattingContext;
}
