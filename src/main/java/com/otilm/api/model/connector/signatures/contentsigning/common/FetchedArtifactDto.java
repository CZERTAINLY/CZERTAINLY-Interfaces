package com.otilm.api.model.connector.signatures.contentsigning.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * One artifact the connector fetched while extending a signature, reported so the signing record can testify to what
 * the signature's validation material was built from.
 *
 * <p>
 * The extension operation is the one place where a connector reaches the network on its own, so the manifest is the
 * platform's only account of that traffic.
 * </p>
 */
@Getter
@Setter
@ToString
@Schema(name = "FetchedArtifact",
        description = "An artifact the connector fetched while extending a signature, with its provenance")
public class FetchedArtifactDto {

    @NotBlank(message = "sourceUrl is required")
    @Schema(description = "URL the artifact was fetched from", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "http://crl.example.com/issuing-ca.crl")
    private String sourceUrl;

    @NotNull(message = "kind is required")
    @Schema(description = "What kind of artifact this is", requiredMode = Schema.RequiredMode.REQUIRED)
    private FetchedArtifactKind kind;

    @NotNull(message = "sha256 is required")
    @Schema(description = "SHA-256 over the artifact's encoded bytes, base64-encoded in JSON. It identifies the exact "
            + "bytes fetched, so the record names the artifact rather than only the address it came from.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private byte[] sha256;

    @NotNull(message = "fetchedAt is required")
    @Schema(description = "When the connector fetched the artifact", requiredMode = Schema.RequiredMode.REQUIRED)
    private OffsetDateTime fetchedAt;

    @JsonIgnore
    @Schema(hidden = true)
    @AssertTrue(message = "sha256 must be 32 bytes")
    public boolean isDigestSha256Sized() {
        return sha256 == null || sha256.length == 32;
    }
}
