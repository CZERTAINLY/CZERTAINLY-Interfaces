package com.czertainly.api.model.client.raprofile;

import com.czertainly.api.model.common.RequestAttributeDto;
import com.czertainly.api.model.common.ResponseAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class ActivateAcmeForRaProfileRequest {
    @Schema(description = "ACME Profile UUID",
            required = true)
    private String acmeProfileUuid;
    @Schema(description = "List of Attributes for new Certificate",
            required = true)
    private List<RequestAttributeDto> issueCertificateAttributes;
    @Schema(description = "List of Attributes for Certificate revocation",
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
