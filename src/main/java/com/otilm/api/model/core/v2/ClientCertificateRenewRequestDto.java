package com.otilm.api.model.core.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.otilm.api.model.core.enums.CertificateRequestFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Class representing a request to renew certificate from external clients
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientCertificateRenewRequestDto {

    @Schema(
            description = "True to replace renewed certificate in the associated locations",
            defaultValue = "false"
    )
    private boolean replaceInLocations;

    @Schema(
            description = "Certificate signing request encoded as Base64 string. If not provided, Existing CSR will be used"
    )
    private String request;

    @Schema(
            description = "Certificate signing request format",
            defaultValue = "pkcs10"
    )
    @Builder.Default
    private CertificateRequestFormat format = CertificateRequestFormat.PKCS10;

    // Format (@Size/@Pattern) is enforced at registration, not here: on the verify path a malformed secret
    // must fail the challenge identically to a wrong one, so no format constraint is applied (no format oracle).
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(
            description = "One-time authorization secret for renewing a certificate that has an active "
                    + "registration. Write-only; ignored for certificates without one.",
            accessMode = Schema.AccessMode.WRITE_ONLY
    )
    private String authorizationSecret;
}
