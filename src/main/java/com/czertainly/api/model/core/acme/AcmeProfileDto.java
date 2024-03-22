package com.czertainly.api.model.core.acme;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.client.raprofile.SimplifiedRaProfileDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class AcmeProfileDto extends NameAndUuidDto {
    @Schema(description = "Enabled flag - true = enabled; false = disabled",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean enabled;
    @Schema(description = "ACME Profile description", example = "Sample description")
    private String description;
    @Schema(description = "Terms of Service URL", example = "https://sample-url.com/termsOfService")
    private String termsOfServiceUrl;
    @Schema(description = "Website URL", example = "https://sample-company.com")
    private String websiteUrl;
    @Schema(description = "DNS Resolver IP address", example = "8.8.8.8")
    private String dnsResolverIp;
    @Schema(description = "DNS Resolver port number", example = "53")
    private String dnsResolverPort;
    @Schema(description = "RA Profile")
    private SimplifiedRaProfileDto raProfile;
    @Schema(description = "Retry interval for ACME client requests", example = "30")
    private Integer retryInterval;
    @Schema(description = "Disable new Orders (change in Terms of Service)", example = "false")
    private Boolean termsOfServiceChangeDisable;
    @Schema(description = "Order validity", example = "36000")
    private Integer validity;
    @Schema(description = "ACME Directory URL", example = "https://some-server.com/api/v1/protocols/acme/profile1/directory")
    private String directoryUrl;
    @Schema(description = "Changes of Terms of Service URL", example = "https://some-company.com/termsOfService/change")
    private String termsOfServiceChangeUrl;
    @Schema(description = "Require Contact information for new Account", example = "true")
    private Boolean requireContact;
    @Schema(description = "Require new Account to agree on Terms of Service", example = "true")
    private Boolean requireTermsOfService;
    @Schema(description = "List of Attributes to issue a Certificate")
    private List<ResponseAttributeDto> issueCertificateAttributes;
    @Schema(description = "List of Attributes to revoke a Certificate")
    private List<ResponseAttributeDto> revokeCertificateAttributes;
    @Schema(description = "List of Custom Attributes")
    private List<ResponseAttributeDto> customAttributes;

    public AcmeProfileDto() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public SimplifiedRaProfileDto getRaProfile() {
        return raProfile;
    }

    public void setRaProfile(SimplifiedRaProfileDto raProfile) {
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

    public Boolean isTermsOfServiceChangeDisable() {
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

    public Boolean isRequireContact() {
        return requireContact;
    }

    public void setRequireContact(Boolean requireContact) {
        this.requireContact = requireContact;
    }

    public Boolean isRequireTermsOfService() {
        return requireTermsOfService;
    }

    public void setRequireTermsOfService(Boolean requireTermsOfService) {
        this.requireTermsOfService = requireTermsOfService;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getTermsOfServiceChangeUrl() {
        return termsOfServiceChangeUrl;
    }

    public void setTermsOfServiceChangeUrl(String termsOfServiceChangeUrl) {
        this.termsOfServiceChangeUrl = termsOfServiceChangeUrl;
    }

    public List<ResponseAttributeDto> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<ResponseAttributeDto> customAttributes) {
        this.customAttributes = customAttributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("isEnabled", enabled)
                .append("description", description)
                .append("termsOfServiceUrl", termsOfServiceUrl)
                .append("websiteUrl", websiteUrl)
                .append("dnsResolverIp", dnsResolverIp)
                .append("dnsResolverPort", dnsResolverPort)
                .append("raProfile", raProfile)
                .append("retryInterval", retryInterval)
                .append("termsOfServiceChangeDisable", termsOfServiceChangeDisable)
                .append("validity", validity)
                .append("directoryUrl", directoryUrl)
                .append("termsOfServiceChangeUrl", termsOfServiceChangeUrl)
                .append("requireContact", requireContact)
                .append("requireTermsOfService", requireTermsOfService)
                .append("issueCertificateAttributes", issueCertificateAttributes)
                .append("revokeCertificateAttributes", revokeCertificateAttributes)
                .append("customAttributes", customAttributes)
                .toString();
    }
}
