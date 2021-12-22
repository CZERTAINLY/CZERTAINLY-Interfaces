package com.czertainly.api.model.discovery;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public class DiscoveryCertificatesDto {
    @Schema(description = "UUID of the Certificate", required = true)
    private String uuid;
    @Schema(description = "Certificate common name", required = true)
    private String commonName;
    @Schema(description = "Certificate Serial Number", required = true)
    private String serialNumber;
    @Schema(description = "Issuer common name", required = true)
    private String issuerCommonName;
    @Schema(description = "Certificate validity start date", required = true)
    private Date notBefore;
    @Schema(description = "Certificate expiration date", required = true)
    private Date notAfter;
    @Schema(description = "SHA256 thumbprint of the certificate", required = true)
    private String fingerprint;
    @Schema(description = "Base64 encoded Certificate content", required = true)
    private String certificateContent;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getIssuerCommonName() {
        return issuerCommonName;
    }

    public void setIssuerCommonName(String issuerCommonName) {
        this.issuerCommonName = issuerCommonName;
    }

    public Date getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
    }

    public Date getNotAfter() {
        return notAfter;
    }

    public void setNotAfter(Date notAfter) {
        this.notAfter = notAfter;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getCertificateContent() {
        return certificateContent;
    }

    public void setCertificateContent(String certificateContent) {
        this.certificateContent = certificateContent;
    }
}
