package com.czertainly.api.model.core.acme;

import java.util.List;

/**
 * This class contains set of properties to represent
 * the account object from ACME.
 */
public class Account {

    /**
     * Status of the ACME account registered with the ACME server.Possible values are "valid", "deactivated",
     * and "revoked".  The value "deactivated" should be used to indicate client-initiated
     * deactivation whereas "revoked" should be used to indicate server-initiated deactivation.
     **/
    private AccountStatus status;

    /**
     * Contact field in the ACME account attribute. This contains the array of URLs that the server can use
     * to contact the client for issues related to this account.
     * This is an optional parameter.
     */
    private List<String> contact;

    /**
     * This field represents the status of the terms of service agreed or not. This field will be included in a
     * new account request indicating the client has agreed to the termsOfService. This field cannot be updated by the
     * client
     * This is a non-mandatory field
     */
    private boolean termsOfServiceAgreed;

    /**
     * If this field is present
     * with the value "true", then the server MUST NOT create a new
     * account if one does not already exist.  This allows a client to
     * look up an account URL based on an account key
     */
    private boolean onlyReturnExisting;
    /**
     * This field indicates the binding and approval from the owner of NON-ACME account to this ACME account.
     * This is an optional field and cannot be updated by the client
     */
    private Object externalAccountBinding;

    /**
     * Represents the URL of from which the list of orders for this account can be fetched.
     * This is a mandatory field.
     */
    private String orders;

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

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

    public Object getExternalAccountBinding() {
        return externalAccountBinding;
    }

    public void setExternalAccountBinding(Object externalAccountBinding) {
        this.externalAccountBinding = externalAccountBinding;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public boolean isOnlyReturnExisting() {
        return onlyReturnExisting;
    }

    public void setOnlyReturnExisting(boolean onlyReturnExisting) {
        this.onlyReturnExisting = onlyReturnExisting;
    }
}
