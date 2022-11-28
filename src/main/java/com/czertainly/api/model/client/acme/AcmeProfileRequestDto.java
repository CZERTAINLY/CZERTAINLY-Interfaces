package com.czertainly.api.model.client.acme;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class AcmeProfileRequestDto {

    @Schema(
            description = "Name of the ACME Profile",
            required = true,
            example = "Profile Name 1"
    )
    private String name;

    @Schema(
            description = "Description of the ACME Profile",
            example = "Sample description"
    )
    private String description;
    @Schema(
            description = "Terms of Service URL",
            example = "https://sample-url.com/termsOfService"
    )
    private String termsOfServiceUrl;
    @Schema(
            description = "Website URL",
            example = "https://sample-url.com"
    )
    private String websiteUrl;
    @Schema(
            description = "DNS Resolver IP address",
            defaultValue = "System Default",
            example = "8.8.8.8"
    )
    private String dnsResolverIp;
    @Schema(
            description = "DNS Resolver port number",
            defaultValue = "53",
            example = "53"
    )
    private String dnsResolverPort;
    @Schema(
            description = "RA Profile UUID",
            example = "6b55de1c-844f-11ec-a8a3-0242ac120002"
    )
    private String raProfileUuid;
    @Schema(
            description = "Retry interval for the Orders",
            defaultValue = "30",
            example = "60"
    )
    private Integer retryInterval;
    @Schema(
            description = "Order Validity",
            defaultValue = "36000",
            example = "3000"
    )
    private Integer validity;
    @Schema(
            description = "List of Attributes to issue Certificate",
            required = true
    )
    private List<RequestAttributeDto> issueCertificateAttributes;
    @Schema(
            description = "List of Attributes to revoke Certificate",
            required = true
    )
    private List<RequestAttributeDto> revokeCertificateAttributes;
    @Schema(
            description = "Require contact information for new Account",
            defaultValue = "false",
            example = "true"
    )
    private Boolean requireContact;
    @Schema(
            description = "Require new Account to agree on Terms of Service",
            defaultValue = "false",
            example = "false"
    )
    private Boolean requireTermsOfService;
    @Schema(description = "List of Custom Attributes")
    private List<RequestAttributeDto> customAttributes;

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

    public List<RequestAttributeDto> getIssueCertificateAttributes() {
        return issueCertificateAttributes;
    }

    public void setIssueCertificateAttributes(List<RequestAttributeDto> issueCertificateAttributes) {
        this.issueCertificateAttributes = issueCertificateAttributes;
    }

    public List<RequestAttributeDto> getRevokeCertificateAttributes() {
        return revokeCertificateAttributes;
    }

    public void setRevokeCertificateAttributes(List<RequestAttributeDto> revokeCertificateAttributes) {
        this.revokeCertificateAttributes = revokeCertificateAttributes;
    }

    public String getRaProfileUuid() {
        return raProfileUuid;
    }

    public void setRaProfileUuid(String raProfileUuid) {
        this.raProfileUuid = raProfileUuid;
    }

    public Integer getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(Integer retryInterval) {
        this.retryInterval = retryInterval;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RequestAttributeDto> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<RequestAttributeDto> customAttributes) {
        this.customAttributes = customAttributes;
    }

    public AcmeProfileRequestDto() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("description", description)
                .append("termsOfServiceUrl", termsOfServiceUrl)
                .append("websiteUrl", websiteUrl)
                .append("dnsResolverIp", dnsResolverIp)
                .append("dnsResolverPort", dnsResolverPort)
                .append("raProfileUuid", raProfileUuid)
                .append("retryInterval", retryInterval)
                .append("validity", validity)
                .append("issueCertificateAttributes", issueCertificateAttributes)
                .append("revokeCertificateAttributes", revokeCertificateAttributes)
                .append("requireContact", requireContact)
                .append("requireTermsOfService", requireTermsOfService)
                .append("customAttributes", customAttributes)
                .toString();
    }
}
