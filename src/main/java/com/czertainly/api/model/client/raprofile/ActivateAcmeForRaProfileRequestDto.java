package com.czertainly.api.model.client.raprofile;

import com.czertainly.api.model.common.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class ActivateAcmeForRaProfileRequestDto {
    @Schema(description = "ACME Profile UUID",
            required = true)
    private String acmeProfileUuid;
    @Schema(description = "List of Attributes to issue Certificate",
            required = true)
    private List<RequestAttributeDto> issueCertificateAttributes;
    @Schema(description = "List of Attributes to revoke Certificate",
            required = true)
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
