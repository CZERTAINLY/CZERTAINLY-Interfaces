package com.czertainly.api.model.client.acme;

import com.czertainly.api.model.core.acme.AccountStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Set of properties to represent the Account object from ACME.
 */
public class AcmeAccountListResponseDto {

    @Schema(
            description = "ID of the Account",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "HJAT6gc7i6"
    )
    private String accountId;
    @Schema(
            description = "UUID of the Account",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "6b55de1c-844f-11ec-a8a3-0242ac120002"
    )
    private String uuid;
    @Schema(
            description = "Enabled flag. true = enabled, false=disabled",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "false"
    )
    private Boolean enabled;
    @Schema(
            description = "Total number of Orders",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "4"
    )
    private Integer totalOrders;
    @Schema(
            description = "Status of the Account",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "VALID"
    )
    private AccountStatus status;
    @Schema(
            description = "Name of the RA Profile",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "RAProfile1"
    )
    private String raProfileName;
    @Schema(
            description = "Name of the ACME Profile",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "ACMEProfile1"
    )
    private String acmeProfileName;

    @Schema(
            description = "UUID of the ACME Profile",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "6b55de1c-844f-11ec-a8a3-0242ac120002"
    )
    private String acmeProfileUuid;

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

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

    public String getAcmeProfileUuid() {
        return acmeProfileUuid;
    }

    public void setAcmeProfileUuid(String acmeProfileUuid) {
        this.acmeProfileUuid = acmeProfileUuid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("accountId", accountId)
                .append("uuid", uuid)
                .append("enabled", enabled)
                .append("totalOrders", totalOrders)
                .append("status", status)
                .append("raProfileName", raProfileName)
                .append("acmeProfileName", acmeProfileName)
                .append("acmeProfileUuid", acmeProfileUuid)
                .toString();
    }
}
