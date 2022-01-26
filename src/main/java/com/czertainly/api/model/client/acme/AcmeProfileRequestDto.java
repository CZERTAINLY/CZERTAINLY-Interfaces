package com.czertainly.api.model.client.acme;

import com.czertainly.api.model.common.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class AcmeProfileRequestDto {

    @Schema(
            description = "Name of the ACME Profile"
    )
    private String name;

    @Schema(
            description = "Description of the Profile"
    )
    private String description;
    @Schema(
            description = "Terms of Servicce URL"
    )
    private String termsOfServiceUrl;
    @Schema(
            description = "Website URL"
    )
    private String websiteUrl;
    @Schema(
            description = "DNS Resolver IP"
    )
    private String dnsResolverIp;
    @Schema(
            description = "DNS Resolver Port"
    )
    private String dnsResolverPort;
    @Schema(
            description = "RA Profile UUID"
    )
    private String raProfileUuid;
    @Schema(
            description = "Retry interval for the Orders"
    )
    private Integer retryInterval;
    @Schema(
            description = "Change needed for new Terms of Service"
    )
    private Boolean termsOfServiceChangeApproval;

    @Schema(
            description = "Updated Terms of Service URL"
    )
    private String termsOfServiceChangeUrl;
    @Schema(
            description = "Order Validity"
    )
    private Integer validity;
    @Schema(
            description = "List of Attributes for new Certificate issuance",
            required = true
    )
    private List<RequestAttributeDto> issueCertificateAttributes;
    @Schema(
            description = "List of Attributes for Certificate revocation",
            required = true
    )
    private List<RequestAttributeDto> revokeCertificateAttributes;
    @Schema(
            description = "Is Contact mandatory for new account?"
    )
    private Boolean insistContact;
    @Schema(
            description = "is new Account must agree to terms of service?"
    )
    private Boolean insistTermsOfService;

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

    public Boolean getTermsOfServiceChangeApproval() {
        return termsOfServiceChangeApproval;
    }

    public void setTermsOfServiceChangeApproval(Boolean termsOfServiceChangeApproval) {
        this.termsOfServiceChangeApproval = termsOfServiceChangeApproval;
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

    public Boolean getInsistContact() {
        return insistContact;
    }

    public void setInsistContact(Boolean insistContact) {
        this.insistContact = insistContact;
    }

    public Boolean getInsistTermsOfService() {
        return insistTermsOfService;
    }

    public void setInsistTermsOfService(Boolean insistTermsOfService) {
        this.insistTermsOfService = insistTermsOfService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTermsOfServiceChangeUrl() {
        return termsOfServiceChangeUrl;
    }

    public void setTermsOfServiceChangeUrl(String termsOfServiceChangeUrl) {
        this.termsOfServiceChangeUrl = termsOfServiceChangeUrl;
    }

    public AcmeProfileRequestDto() {
    }
}
