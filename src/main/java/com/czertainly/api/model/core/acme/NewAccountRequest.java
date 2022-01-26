package com.czertainly.api.model.core.acme;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Set of properties to represent
 * the Account object from ACME.
 */
public class NewAccountRequest {

    /**
     * Contact field in the ACME Account attribute. This contains the array of URLs that the server can use
     * to contact the client for issues related to this Account.
     * This is an optional parameter.
     */
    @Schema(description = "List of contacts for the Account. Required if the required flag is set in ACME Profile")
    private List<String> contact;

    /**
     * This field represents the status of the Terms of Service agreed or not. This field will be included in a
     * new Account request indicating the client has agreed to the termsOfService. This field cannot be updated by the
     * client.
     * This is a non-mandatory field.
     */
    @Schema(description = "Terms of Service agreed flag. true = Yes, false = No. Required if the required flag is set in ACME Profile")
    private boolean termsOfServiceAgreed;

    /**
     * This field represents if the server should return only the Account that is already available. If the value is set
     * as true and the server cannot find any data, then the server can return error. This field will be included in a
     * new Account request indicating the client has agreed to the termsOfService. This field cannot be updated by the
     * client.
     * This is a non-mandatory field.
     */
    @Schema(description = "Return existing Account only flag")
    private boolean onlyReturnExisting;

    /**
     * Represents the URL of from which the list of orders for this Account can be fetched.
     * This is a mandatory field.
     */
    @Schema(description = "URL to get the list of Orders for the Account")
    private String orders;


    public List<String> getContact() {
        return contact;
    }

    public void setContact(List<String> contact) {
        this.contact = contact;
    }

    public boolean isTermsOfServiceAgreed() {
        return termsOfServiceAgreed;
    }

    public void setTermsOfServiceAgreed(boolean termsOfServiceAgreed) {
        this.termsOfServiceAgreed = termsOfServiceAgreed;
    }

    public boolean isOnlyReturnExisting() {
        return onlyReturnExisting;
    }

    public void setOnlyReturnExisting(boolean onlyReturnExisting) {
        this.onlyReturnExisting = onlyReturnExisting;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }
}
