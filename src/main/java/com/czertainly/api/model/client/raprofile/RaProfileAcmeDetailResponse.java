package com.czertainly.api.model.client.raprofile;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.ResponseAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class RaProfileAcmeDetailResponse extends NameAndUuidDto {

    @Schema(description = "Available flag - true = yes; false = no",
            required = true)
    private Boolean isAcmeAvailable;
    @Schema(description = "ACME directory URL",
            required = true)
    private String directoryUrl;
    @Schema(description = "List of Attributes for new Certificate",
            required = true)
    private List<ResponseAttributeDto> issueCertificateAttributes;
    @Schema(description = "List of Attributes for Certificate revocation",
            required = true)
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
