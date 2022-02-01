package com.czertainly.api.model.core.acme;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Parameters and fields that are part of the ACME Directory response for the Directory API
 */
public class Directory {
    /**
     * URL for the new Nonce. This url will be used by the ACME clients to request for
     * new Nonce values.
     */
    @Schema(description = "URL to get new Nonce",
            required = true)
    private String newNonce;

    /**
     * URL for the new Account. This url will be used by the ACME clients to request for
     * new Account registration.
     */
    @Schema(description = "URL for the new Account",
            required = true)
    private String newAccount;

    /**
     * URL for the new Order. This url will be used by the ACME clients to request for
     * new Order
     */
    @Schema(description = "URL for the new Order",
            required = true)
    private String newOrder;

    /**
     * URL for the new Authorization. This url will be used by the ACME clients to request for
     * new Authz request
     */
    @Schema(description = "URL for the new Authorization",
            required = true)
    private String newAuthz;

    /**
     * URL for revoking a Certificate. This url will be used by the ACME clients to request for
     * new Certificate revocation
     */
    @Schema(description = "URL for revoking a certificate",
            required = true)
    private String revokeCert;

    /**
     * URL for changing the key of an Account. This url will be used by the ACME clients to request for
     * a new key change for an Account
     */
    @Schema(description = " URL for changing the key of an Account",
            required = true)
    private String keyChange;

    /**
     * Metadata for the Directory object. This will contain the metadata like termsOfService etc..
     */
    @Schema(description = "Metadata for the Directory object",
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("newNonce", newNonce)
                .append("newAccount", newAccount)
                .append("newOrder", newOrder)
                .append("newAuthz", newAuthz)
                .append("revokeCert", revokeCert)
                .append("keyChange", keyChange)
                .append("meta", meta)
                .toString();
    }
}
