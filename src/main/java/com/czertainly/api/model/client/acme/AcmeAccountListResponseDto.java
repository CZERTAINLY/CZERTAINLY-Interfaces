package com.czertainly.api.model.client.acme;

import com.czertainly.api.model.core.acme.AccountStatus;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This class contains set of properties to represent
 * the Account object from ACME.
 */
public class AcmeAccountListResponseDto {

    @Schema(
            description = "ID of the Account",
            required = true
    )
    private String accountId;
    @Schema(
            description = "UUID of the Account",
            required = true
    )
    private String uuid;
    @Schema(
            description = "Enabled flag. true = enabled, false=disabled",
            required = true
    )
    private Boolean isEnabled;
    @Schema(
            description = "Total number of Orders",
            required = true
    )
    private Integer totalOrders;
    @Schema(
            description = "Status of the Account",
            required = true
    )
    private AccountStatus status;
    @Schema(
            description = "Name of the RA Profile",
            required = true
    )
    private String raProfileName;
    @Schema(
            description = "Name of the ACME Profile",
            required = true
    )
    private String acmeProfileName;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Integer getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Integer totalOrders) {
        this.totalOrders = totalOrders;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }


    public String getRaProfileName() {
        return raProfileName;
    }

    public void setRaProfileName(String raProfileName) {
        this.raProfileName = raProfileName;
    }

    public String getAcmeProfileName() {
        return acmeProfileName;
    }

    public void setAcmeProfileName(String acmeProfileName) {
        this.acmeProfileName = acmeProfileName;
    }
}
