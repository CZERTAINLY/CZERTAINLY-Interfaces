package com.czertainly.api.model.client.raprofile;

import com.czertainly.api.model.common.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class ActivateAcmeForRaProfileRequestDto {
    @Schema(description = "List of Attributes to issue Certificate",
            required = true)
    private List<RequestAttributeDto> issueCertificateAttributes;

    @Schema(description = "List of Attributes to revoke Certificate",
            required = true)
    private List<RequestAttributeDto> revokeCertificateAttributes;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("issueCertificateAttributes", issueCertificateAttributes)
                .append("revokeCertificateAttributes", revokeCertificateAttributes)
                .toString();
    }
}
