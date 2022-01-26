package com.czertainly.api.model.core.acme;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.ResponseAttributeDto;
import com.czertainly.api.model.core.raprofile.RaProfileDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class AcmeProfileDto extends NameAndUuidDto {
    @Schema(description = "Enabled flag - true = enabled; false = disabled",
            required = true)
    private boolean isEnabled;
    @Schema(description = "ACME Profile description")
    private String description;
    @Schema(description = "Terms of Service URL")
    private String termsOfServiceUrl;
    @Schema(description = "Website URL")
    private String websiteUrl;
    @Schema(description = "DNS Resolver IP address")
    private String dnsResolverIp;
    @Schema(description = "DNS Resolver port number")
    private String dnsResolverPort;
    @Schema(description = "RA Profile")
    private RaProfileDto raProfile;
    @Schema(description = "Retry interval for ACME client requests")
    private Integer retryInterval;
    @Schema(description = "Disable new Orders (change in Terms of Service)")
    private Boolean termsOfServiceChangeDisable;
    @Schema(description = "Order validity")
    private Integer validity;
    @Schema(description = "ACME Directory URL")
    private String directoryUrl;
    @Schema(description = "Changes of Terms of Service URL")
    private String termsOfServiceChangeUrl;
    @Schema(description = "Require Contact information for new Account")
    private Boolean requireContact;
    @Schema(description = "Require new Account to agree on Terms of Service")
    private Boolean requireTermsOfService;
    @Schema(description = "List of Attributes to issue a Certificate")
    private List<ResponseAttributeDto> issueCertificateAttributes;
    @Schema(description = "List of Attributes to revoke a Certificate")
    private List<ResponseAttributeDto> revokeCertificateAttributes;

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public String getDnsResolverIp() {
        return dnsResolverIp;
    }

    public void setDnsResolverIp(String dnsResolverIp) {
        this.dnsResolverIp = dnsResolverIp;
    }

    public String getDnsResolverPort() {
        return dnsResolverPort;
    }

    public void setDnsResolverPort(String dnsResolverPort) {
        this.dnsResolverPort = dnsResolverPort;
    }

    public RaProfileDto getRaProfile() {
        return raProfile;
    }

    public void setRaProfile(RaProfileDto raProfile) {
        this.raProfile = raProfile;
    }

    public List<ResponseAttributeDto> getIssueCertificateAttributes() {
        return issueCertificateAttributes;
    }

    public void setIssueCertificateAttributes(List<ResponseAttributeDto> issueCertificateAttributes) {
        this.issueCertificateAttributes = issueCertificateAttributes;
    }

    public List<ResponseAttributeDto> getRevokeCertificateAttributes() {
        return revokeCertificateAttributes;
    }

    public void setRevokeCertificateAttributes(List<ResponseAttributeDto> revokeCertificateAttributes) {
        this.revokeCertificateAttributes = revokeCertificateAttributes;
    }

    public Integer getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(Integer retryInterval) {
        this.retryInterval = retryInterval;
    }

    public Boolean getTermsOfServiceChangeDisable() {
        return termsOfServiceChangeDisable;
    }

    public void setTermsOfServiceChangeDisable(Boolean termsOfServiceChangeDisable) {
        this.termsOfServiceChangeDisable = termsOfServiceChangeDisable;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public String getDirectoryUrl() {
        return directoryUrl;
    }

    public void setDirectoryUrl(String directoryUrl) {
        this.directoryUrl = directoryUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRequireContact() {
        return requireContact;
    }

    public void setRequireContact(Boolean requireContact) {
        this.requireContact = requireContact;
    }

    public Boolean getRequireTermsOfService() {
        return requireTermsOfService;
    }

    public void setRequireTermsOfService(Boolean requireTermsOfService) {
        this.requireTermsOfService = requireTermsOfService;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getTermsOfServiceChangeUrl() {
        return termsOfServiceChangeUrl;
    }

    public void setTermsOfServiceChangeUrl(String termsOfServiceChangeUrl) {
        this.termsOfServiceChangeUrl = termsOfServiceChangeUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public AcmeProfileDto() {
    }
}
