package com.czertainly.api.model.core.acme;


import com.czertainly.api.model.core.authority.RevocationReason;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Fields to be used by the client for revoking a certificate issued to the
 * same Account
 */
public class CertificateRevocationRequest {
    /**
     * The Certificate to be revoked, in the base64url-encoded version of the DER format.  (Note: Because this field
     * uses base64url, and does not include headers, it is different from PEM.).
     */
    @Schema(description = "Certificate in base64url-encoded version of DER format",
            required = true,
            example = "<base64url-encoded version of the DER format>")
    private String certificate;

    /**
     * Reason for the Certificate revocation. The reason is as specified by the RFC5280
     * The value is optional. If none of the reason is given, then the server can leave the reason blank for OCSP
     * meaning the server can set the code for revocation is UNSPECIFIED.
     */
    @Schema(description = "Reason for revocation",
            required = true,
            example = "1")
    private RevocationReason reason;

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public RevocationReason getReason() {
        return reason;
    }

    public void setReason(RevocationReason reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("certificate", certificate)
                .append("reason", reason)
                .toString();
    }
}
