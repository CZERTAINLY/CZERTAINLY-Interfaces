package com.czertainly.api.model.client.acme;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.RequestAttributeDto;

import java.util.List;

public class AcmeProfileRequestDto extends NameAndUuidDto {

    private String description;
    private String termsOfServiceUrl;
    private String websiteUrl;
    private String dnsResolverIp;
    private String dnsResolverPort;
    private String raProfileUuid;
    private Integer retryInterval;
    private Boolean termsOfServiceChangeApproval;
    private Integer validity;
    private List<RequestAttributeDto> issueCertificateAttributes;
    private List<RequestAttributeDto> revokeCertificateAttributes;
    private Boolean insistContact;
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

    public AcmeProfileRequestDto() {
    }
}
