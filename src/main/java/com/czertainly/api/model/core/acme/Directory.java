package com.czertainly.api.model.core.acme;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Parameters and fields that are part of the ACME
 * directory response for the directory API
 */
public class Directory {
    /**
     * URL for the new nonce. This url will be used by the ACME clients to request for
     * new Nonce values.
     */
    @Schema(description = "URL to get new Nonce",
            required = true)
    private String newNonce;

    /**
     * URL for the new account. This url will be used by the ACME clients to request for
     * new account registration.
     */
    @Schema(description = "URL for the new account",
            required = true)
    private String newAccount;

    /**
     * URL for the new Order. This url will be used by the ACME clients to request for
     * new Order for certificates
     */
    @Schema(description = "URL for the new Order",
            required = true)
    private String newOrder;

    /**
     * URL for the new AuthZ. This url will be used by the ACME clients to request for
     * new Authz request
     */
    @Schema(description = "URL for the new AuthZ",
            required = true)
    private String newAuthz;

    /**
     * URL for revoking a certificate. This url will be used by the ACME clients to request for
     * new certificate revocation
     */
    @Schema(description = "URL for revoking a certificate",
            required = true)
    private String revokeCert;

    /**
     * URL for changing the key of an account. This url will be used by the ACME clients to request for
     * a new key change for an account
     */
    @Schema(description = " URL for changing the key of an account",
            required = true)
    private String keyChange;

    /**
     * Metadata for the directory object. This will contain the metadata like termsOfService etc..
     */
    @Schema(description = "Metadata for the directory object",
            required = true)
    private DirectoryMeta meta;

    public String getNewNonce() {
        return newNonce;
    }

    public void setNewNonce(String newNonce) {
        this.newNonce = newNonce;
    }

    public String getNewAccount() {
        return newAccount;
    }

    public void setNewAccount(String newAccount) {
        this.newAccount = newAccount;
    }

    public String getNewOrder() {
        return newOrder;
    }

    public void setNewOrder(String newOrder) {
        this.newOrder = newOrder;
    }

    public String getNewAuthz() {
        return newAuthz;
    }

    public void setNewAuthz(String newAuthz) {
        this.newAuthz = newAuthz;
    }

    public String getRevokeCert() {
        return revokeCert;
    }

    public void setRevokeCert(String revokeCert) {
        this.revokeCert = revokeCert;
    }

    public String getKeyChange() {
        return keyChange;
    }

    public void setKeyChange(String keyChange) {
        this.keyChange = keyChange;
    }

    public DirectoryMeta getMeta() {
        return meta;
    }

    public void setMeta(DirectoryMeta meta) {
        this.meta = meta;
    }
}
