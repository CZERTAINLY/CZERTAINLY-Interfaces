package com.czertainly.api.model.client.raprofile;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.ResponseAttributeDto;

import java.util.List;

public class RaProfileAcmeDetailResponse extends NameAndUuidDto {

    private Boolean isAcmeAvailable;
    private String directoryUrl;
    private List<ResponseAttributeDto> issueCertificateAttributes;
    private List<ResponseAttributeDto> revokeCertificateAttributes;

    public Boolean getAcmeAvailable() {
        return isAcmeAvailable;
    }

    public void setAcmeAvailable(Boolean acmeAvailable) {
        isAcmeAvailable = acmeAvailable;
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

    public String getDirectoryUrl() {
        return directoryUrl;
    }

    public void setDirectoryUrl(String directoryUrl) {
        this.directoryUrl = directoryUrl;
    }
}
