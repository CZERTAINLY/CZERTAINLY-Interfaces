package com.czertainly.api.model.client.raprofile;

import com.czertainly.api.model.common.RequestAttributeDto;

import java.util.List;

public class ActivateAcmeForRaProfileRequest {
    private String acmeProfileUuid;
    private List<RequestAttributeDto> issueCertificateAttributes;
    private List<RequestAttributeDto> revokeCertificateAttributes;

    public String getAcmeProfileUuid() {
        return acmeProfileUuid;
    }

    public void setAcmeProfileUuid(String acmeProfileUuid) {
        this.acmeProfileUuid = acmeProfileUuid;
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
}
