package com.czertainly.api.model.core.acme;

import java.util.List;

/**
 * This class contains set of properties to represent
 * the Order object from the ACME for an Account.
 */
public class Order {
    /**
     * Status represents the status of the order. Possible values are "pending", "ready", "processing", "valid"
     * and "invalid".
     * This is a mandatory field
     */
    private OrderStatus status;

    /**
     * Timestamp after which the server will consider the order as invalid. This is encoded in the time format
     * specified in RFC3339 (2016-01-20T14:09:07.99Z)
     * This is an optional parameter
     */
    private String expires;

    /**
     * Array of the identifier that the order pertains to
     * This is a mandatory parameter
     */
    private List<Identifier> identifiers;

    /**
     * notBefore field in the certificate. The format of the field is as defined in RFC3339
     * (2016-01-20T14:09:07.99Z)
     * This is an optional parameter
     */
    private String notBefore;

    /**
     * notAfter field in the certificate. The format of the field is as defined in RFC3339
     * (2016-01-20T14:09:07.99Z)
     * This is an optional parameter
     */
    private String notAfter;

    /**
     * Errors that occurred during the processing of  order if anything raises
     * This field should be structured as defined in RFC7801
     * This is a non mandatory field
     */
    private ProblemDocument error;

    /**
     * List of authorizations that the client needs to complete for the server to allow the finalization of the order.
     * Only after the authorizations, the server will issue the certificate. This will also include the list of
     * authorizations that client has completed for the same list of identifiers
     */
    private List<String> authorizations;

    /**
     * URL for finalizing the order and asking the server to issue the certificate once the authorizations
     * are satisfied
     */
    private String finalize;

    /**
     * URL for the client to download the certificate that has been issues in response to the order.
     * This is a non-mandatory field
     */
    private String certificate;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

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

    public ProblemDocument getError() {
        return error;
    }

    public void setError(ProblemDocument error) {
        this.error = error;
    }

    public List<String> getAuthorizations() {
        return authorizations;
    }

    public void setAuthorizations(List<String> authorizations) {
        this.authorizations = authorizations;
    }

    public String getFinalize() {
        return finalize;
    }

    public void setFinalize(String finalize) {
        this.finalize = finalize;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

}
