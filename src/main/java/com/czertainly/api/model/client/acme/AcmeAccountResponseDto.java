package com.czertainly.api.model.client.acme;

import com.czertainly.api.model.core.acme.AccountStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * This class contains set of properties to represent
 * the Account object from ACME.
 */
public class AcmeAccountResponseDto {

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
            description = "Enabled flag. enabled=true, disabled=false",
            required = true
    )
    private Boolean isEnabled;
    @Schema(
            description = "Order count for the Account",
            required = true
    )
    private Integer totalOrders;
    @Schema(
            description = "Number of successful Orders",
            required = true
    )
    private Integer successfulOrders;
    @Schema(
            description = "Number of failed Orders",
            required = true
    )
    private Integer failedOrders;
    @Schema(
            description = "Number of pending Orders",
            required = true
    )
    private Integer pendingOrders;
    @Schema(
            description = "Number of Valid Orders",
            required = true
    )
    private Integer validOrders;
    @Schema(
            description = "Number of processing Orders",
            required = true
    )
    private Integer processingOrders;
    @Schema(
            description = "Status of the Account",
            required = true
    )
    private AccountStatus status;
    @Schema(
            description = "Contact information",
            required = true
    )
    private List<String> contact;
    @Schema(
            description = "Terms of Service Agreed",
            required = true
    )
    private Boolean termsOfServiceAgreed;
    @Schema(
            description = "Name of the RA Profile",
            required = true
    )
    private String raProfileName;
    @Schema(
            description = "UUID of the RA Profile",
            required = true
    )
    private String raProfileUuid;
    @Schema(
            description = "Name of the ACME Profile",
            required = true
    )
    private String acmeProfileName;
    @Schema(
            description = "UUID of the ACME Profile",
            required = true
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

    public List<String> getContact() {
        return contact;
    }

    public void setContact(List<String> contact) {
        this.contact = contact;
    }

    public Boolean getTermsOfServiceAgreed() {
        return termsOfServiceAgreed;
    }

    public void setTermsOfServiceAgreed(Boolean termsOfServiceAgreed) {
        this.termsOfServiceAgreed = termsOfServiceAgreed;
    }

    public String getRaProfileName() {
        return raProfileName;
    }

    public void setRaProfileName(String raProfileName) {
        this.raProfileName = raProfileName;
    }

    public String getRaProfileUuid() {
        return raProfileUuid;
    }

    public void setRaProfileUuid(String raProfileUuid) {
        this.raProfileUuid = raProfileUuid;
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
