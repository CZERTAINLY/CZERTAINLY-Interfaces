package com.czertainly.api.model.core.acme;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Set of properties to represent
 * the Order object from the ACME for an Account.
 */
public class Order {
    /**
     * Status represents the status of the Order. Possible values are "pending", "ready", "processing", "valid"
     * and "invalid".
     * This is a mandatory field
     */
    @Schema(description = "Status of the Order",
            required = true)
    private OrderStatus status;

    /**
     * Timestamp after which the server will consider the Order as invalid. This is encoded in the time format
     * specified in RFC3339 (2016-01-20T14:09:07.99Z)
     * This is an optional parameter
     */
    @Schema(description = "Expiry time of the Order")
    private String expires;

    /**
     * Array of the Identifier that the Order pertains to
     * This is a mandatory parameter
     */
    @Schema(description = "List of Order Identifiers", required = true)
    private List<Identifier> identifiers;

    /**
     * Value of notBefore field in the Certificate. The format of the field is as defined in RFC3339
     * (2016-01-20T14:09:07.99Z)
     * This is an optional parameter
     */
    @Schema(description = "Value of notBefore field in the Certificate")
    private String notBefore;

    /**
     * Value of notAfter field in the Certificate. The format of the field is as defined in RFC3339
     * (2016-01-20T14:09:07.99Z)
     * This is an optional parameter
     */
    @Schema(description = "Value of notAfter field in the Certificate")
    private String notAfter;

    /**
     * Errors that occurred during the processing of  Order if anything raises
     * This field should be structured as defined in RFC7801
     * This is a non-mandatory field
     */
    @Schema(description = "Errors in Order")
    private ProblemDocument error;

    /**
     * List of Authorizations that the client needs to complete for the server to allow the finalization of the Order.
     * Only after the Authorizations, the server will issue the Certificate. This will also include the list of
     * Authorizations that client has completed for the same list of identifiers
     */
    @Schema(description = "List of URLs to check for Authorizations")
    private List<String> authorizations;

    /**
     * URL for finalizing the Order and asking the server to issue the Certificate once the Authorizations
     * are satisfied
     */
    @Schema(description = "URL to finalize the Order. Mandatory if the Order is in ready state")
    private String finalize;

    /**
     * URL for the client to download the Certificate that has been issues in response to the Order.
     * This is a non-mandatory field
     */
    @Schema(description = "URL to download the Certificate")
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
