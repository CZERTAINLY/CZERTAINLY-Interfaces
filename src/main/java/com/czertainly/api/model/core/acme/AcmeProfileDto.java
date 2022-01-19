package com.czertainly.api.model.core.acme;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.ResponseAttributeDto;
import com.czertainly.api.model.core.raprofile.RaProfileDto;

import java.util.List;

public class AcmeProfileDto extends NameAndUuidDto {

    private boolean isEnabled;
    private String description;
    private String termsOfServiceUrl;
    private String dnsResolverIp;
    private String dnsResolverPort;
    private RaProfileDto raProfile;
    private Integer retryInterval;
    private Boolean termsOfServiceChangeApproval;
    private Integer validity;
    private String directoryUrl;
    private Boolean insistContact;
    private Boolean insistTermsOfService;
    private List<ResponseAttributeDto> issueCertificateAttributes;
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

    public AcmeProfileDto() {
    }
}
