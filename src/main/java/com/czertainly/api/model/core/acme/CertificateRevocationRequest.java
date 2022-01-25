package com.czertainly.api.model.core.acme;


import com.czertainly.api.model.core.authority.RevocationReason;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This class contains the fields to be used by the client for revoking a certificate issued to the
 * same account
 */
public class CertificateRevocationRequest {
    /**
     * The certificate to be revoked, in the base64url-encoded version of the DER format.  (Note: Because this field
     * uses base64url, and does not include headers, it is different from PEM.)
     */
    @Schema(description = "Certificate in base64url-encoded version of DER format",
            required = true)
    private String certificate;

    /**
     * Reason for the certificate revocation. The reason is as specified by the RFC5280
     * The value is optional. If none of the reason is given, then the server can leave the reason blank for OCSP
     * meaning the server can set the code for revocation is UNSPECIFIED
     */
    @Schema(description = "Reason for revocation",
            required = true)
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
}
