package com.czertainly.api.model.client.raprofile;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class RaProfileAcmeDetailResponseDto extends NameAndUuidDto {

    @Schema(description = "ACME availability flag - true = yes; false = no",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isAcmeAvailable;
    @Schema(description = "ACME Directory URL",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String directoryUrl;
    @Schema(description = "List of Attributes to issue Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ResponseAttributeDto> issueCertificateAttributes;
    @Schema(description = "List of Attributes to revoke Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED)
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
