package com.otilm.api.model.connector.signatures.contentsigning.common;

import com.otilm.api.model.common.enums.cryptography.DigestAlgorithm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Result of either imprint computation: the message imprint the platform is to have timestamped, and the algorithm that
 * produced it.
 */
@Getter
@Setter
@ToString
@Schema(name = "TimestampImprintResponse",
        description = "Message imprint to be timestamped, and the digest algorithm that produced it")
public class TimestampImprintResponseDto {

    @NotNull(message = "imprint is required")
    @Schema(description = "The message imprint, base64-encoded in JSON", requiredMode = Schema.RequiredMode.REQUIRED)
    private byte[] imprint;

    @NotNull(message = "digestAlgorithm is required")
    @Schema(description = "Digest algorithm the connector used to compute the imprint. The platform requests a "
            + "timestamp over exactly this imprint under exactly this algorithm.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private DigestAlgorithm digestAlgorithm;
}
