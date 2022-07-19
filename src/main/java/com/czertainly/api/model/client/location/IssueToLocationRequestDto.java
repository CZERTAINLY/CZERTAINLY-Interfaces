package com.czertainly.api.model.client.location;

import com.czertainly.api.model.common.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Class representing client issue Certificate request for Location
 */
public class IssueToLocationRequestDto {

    @Schema(
            description = "RA Profile UUID",
            required = true
    )
    private String raProfileUuid;

    @Schema(
            description = "List of CSR Attributes for Location",
            required = true
    )
    private List<RequestAttributeDto> csrAttributes;

    @Schema(
            description = "List of certificate issue Attributes for RA Profile",
            required = true
    )
    private List<RequestAttributeDto> issueAttributes;

    public String getRaProfileUuid() {
        return raProfileUuid;
    }

    public void setRaProfileUuid(String raProfileUuid) {
        this.raProfileUuid = raProfileUuid;
    }

    public List<RequestAttributeDto> getCsrAttributes() {
        return csrAttributes;
    }

    public void setCsrAttributes(List<RequestAttributeDto> csrAttributes) {
        this.csrAttributes = csrAttributes;
    }

    public List<RequestAttributeDto> getIssueAttributes() {
        return issueAttributes;
    }

    public void setIssueAttributes(List<RequestAttributeDto> issueAttributes) {
        this.issueAttributes = issueAttributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("raProfileUuid", raProfileUuid)
                .append("csrAttributes", csrAttributes)
                .append("issueAttributes", issueAttributes)
                .toString();
    }
}
