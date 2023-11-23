package com.czertainly.api.model.client.acme;

import com.czertainly.api.model.client.raprofile.SimplifiedRaProfileDto;
import com.czertainly.api.model.core.acme.AccountStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Set of properties to represent the Account object from ACME.
 */
public class AcmeAccountResponseDto {

    @Schema(
            description = "ID of the Account",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "TtrgfYTR6F"
    )
    private String accountId;
    @Schema(
            description = "UUID of the Account",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "6b55de1c-844f-11ec-a8a3-0242ac120002"
    )
    private String uuid;
    @Schema(
            description = "Enabled flag. enabled=true, disabled=false",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "false"
    )
    private Boolean enabled;
    @Schema(
            description = "Order count for the Account",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "23"
    )
    private Integer totalOrders;
    @Schema(
            description = "Number of successful Orders",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "2"
    )
    private Integer successfulOrders;
    @Schema(
            description = "Number of failed Orders",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "239"
    )
    private Integer failedOrders;
    @Schema(
            description = "Number of pending Orders",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "24"
    )
    private Integer pendingOrders;
    @Schema(
            description = "Number of valid Orders",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "23"
    )
    private Integer validOrders;
    @Schema(
            description = "Number of processing Orders",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "27"
    )
    private Integer processingOrders;
    @Schema(
            description = "Status of the Account",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "VALID"
    )
    private AccountStatus status;
    @Schema(
            description = "Contact information",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "mailto: someadmin@domain.com"
    )
    private List<String> contact;
    @Schema(
            description = "Terms of Service Agreed",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "true"
    )
    private Boolean termsOfServiceAgreed;
    @Schema(
            description = "RA Profile",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "RA Profile 1"
    )
    private SimplifiedRaProfileDto raProfile;
    @Schema(
            description = "Name of the ACME Profile",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "ACME Profile 1"
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

    public List<String> getContact() {
        return contact;
    }

    public void setContact(List<String> contact) {
        this.contact = contact;
    }

    public Boolean isTermsOfServiceAgreed() {
        return termsOfServiceAgreed;
    }

    public void setTermsOfServiceAgreed(Boolean termsOfServiceAgreed) {
        this.termsOfServiceAgreed = termsOfServiceAgreed;
    }

    public SimplifiedRaProfileDto getRaProfile() {
        return raProfile;
    }

    public void setRaProfile(SimplifiedRaProfileDto raProfile) {
        this.raProfile = raProfile;
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

    public Integer getSuccessfulOrders() {
        return successfulOrders;
    }

    public void setSuccessfulOrders(Integer successfulOrders) {
        this.successfulOrders = successfulOrders;
    }

    public Integer getFailedOrders() {
        return failedOrders;
    }

    public void setFailedOrders(Integer failedOrders) {
        this.failedOrders = failedOrders;
    }

    public Integer getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(Integer pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public Integer getValidOrders() {
        return validOrders;
    }

    public void setValidOrders(Integer validOrders) {
        this.validOrders = validOrders;
    }

    public Integer getProcessingOrders() {
        return processingOrders;
    }

    public void setProcessingOrders(Integer processingOrders) {
        this.processingOrders = processingOrders;
    }
}
