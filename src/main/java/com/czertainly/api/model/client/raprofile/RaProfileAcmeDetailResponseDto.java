package com.czertainly.api.model.client.raprofile;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.attribute.ResponseAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class RaProfileAcmeDetailResponseDto extends NameAndUuidDto {

    @Schema(description = "ACME availability flag - true = yes; false = no",
            required = true)
    private Boolean isAcmeAvailable;
    @Schema(description = "ACME Directory URL",
            required = true)
    private String directoryUrl;
    @Schema(description = "List of Attributes to issue Certificate",
            required = true)
    private List<ResponseAttributeDto> issueCertificateAttributes;
    @Schema(description = "List of Attributes to revoke Certificate",
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
