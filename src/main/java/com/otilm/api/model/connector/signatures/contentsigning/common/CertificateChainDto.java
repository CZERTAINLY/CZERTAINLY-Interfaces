package com.otilm.api.model.connector.signatures.contentsigning.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The certificate chain the connector's validation engine works from, together with the designation of where that chain
 * stops.
 *
 * <p>
 * The engine needs the terminus to decide whether to demand revocation data for a certificate.
 * </p>
 */
@Getter
@Setter
@ToString
@Schema(name = "CertificateChain",
        description = "Certificate chain with its trust-anchor designation. Both lists must be present; either may "
                + "be empty.")
public class CertificateChainDto {

    @NotNull(message = "certificates may be an empty list, but must be present")
    @Schema(description = "The chain to build validation material for: DER-encoded X.509 certificates covering every "
            + "signature and timestamp in the document, base64-encoded in JSON. May be an empty list, but must be "
            + "present.", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<@NotNull(message = "certificates must not contain null items") byte[]> certificates;

    @NotNull(message = "trustAnchors may be an empty list, but must be present")
    @Schema(description = "DER-encoded X.509 certificates the platform resolved as the termini of the chains above, "
            + "base64-encoded in JSON. A connector treats a certificate as an anchor only if it appears here, and an "
            + "empty list therefore means no anchor is designated and every certificate is treated as untrusted. The "
            + "designation comes from the chain the platform already resolved, never from a trust configuration.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<@NotNull(message = "trustAnchors must not contain null items") byte[]> trustAnchors;
}
