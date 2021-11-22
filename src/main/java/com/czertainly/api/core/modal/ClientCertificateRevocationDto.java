package com.czertainly.api.core.modal;

import com.czertainly.api.model.ca.RevocationReason;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class with parameter to revoke any certificate.
 */
public class ClientCertificateRevocationDto {

    @Schema(description = "Certificate serial number",
            required = true)
    private String certificateSN;

    @Schema(description = "Issuer domain name",
            required = true)
    private String issuerDN;

    @Schema(description = "Revocation reason",
            implementation = RevocationReason.class,
            required = true)
    private RevocationReason reason;

    public String getCertificateSN() {
        return certificateSN;
    }

    public void setCertificateSN(String certificateSN) {
        this.certificateSN = certificateSN;
    }

    public String getIssuerDN() {
        return issuerDN;
    }

    public void setIssuerDN(String issuerDN) {
        this.issuerDN = issuerDN;
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
                .append("certificateSN", certificateSN)
                .append("issuerDN", issuerDN)
                .append("reason", reason)
                .toString();
    }
}
