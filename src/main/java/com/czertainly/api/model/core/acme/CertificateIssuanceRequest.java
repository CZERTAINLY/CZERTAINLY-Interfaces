package com.czertainly.api.model.core.acme;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Class represents the objects and parameters for the client to be sent for the
 * new order.
 */
public class CertificateIssuanceRequest {
    /**
     * List of the identifiers that the client wishes to submit an order for.
     */
    @Schema(description = "List of Identifiers for the Order",
            required = true)
    private List<Identifier> identifiers;

    /**
     * The requested value of notBefore field in the certificate. The date format
     * is as defined in the RFC3339.
     */
    @Schema(description = "Requested value of notBefore field in the certificate")
    private String notBefore;

    /**
     * The requested value of notAfter field in the certificate. The format of the certificate
     * is as defined in the RFC3339.
     */
    @Schema(description = "Requested value of notAfter field in the certificate",
            required = true)
    private String notAfter;

    public List<Identifier> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<Identifier> identifiers) {
        this.identifiers = identifiers;
    }

    public String getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(String notBefore) {
        this.notBefore = notBefore;
    }

    public String getNotAfter() {
        return notAfter;
    }

    public void setNotAfter(String notAfter) {
        this.notAfter = notAfter;
    }
}
