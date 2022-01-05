package com.czertainly.api.model.core.acme;

/**
 * Class represents the dto for the client to send for finalizing the order.
 */
public class CertificateFinalizeRequest {
    /**
     * A CSR encoding the parameters for the certificate being requested [RFC2986].  The CSR is sent in the
     * base64url-encoded version of the DER format.  (Note: Because this field uses base64url, and does not include
     * headers, it is different from PEM.)
     */
    private String csr;

    public String getCsr() {
        return csr;
    }

    public void setCsr(String csr) {
        this.csr = csr;
    }
}
