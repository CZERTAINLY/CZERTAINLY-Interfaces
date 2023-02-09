package com.czertainly.api.model.client.location;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
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
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String raProfileUuid;

    @Schema(
            description = "List of CSR Attributes for Location",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RequestAttributeDto> csrAttributes;

    @Schema(
            description = "List of certificate issue Attributes for RA Profile",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RequestAttributeDto> issueAttributes;

    @Schema(description = "List of Custom Attributes")
    private List<RequestAttributeDto> customAttributes;

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

    public List<RequestAttributeDto> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<RequestAttributeDto> customAttributes) {
        this.customAttributes = customAttributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("raProfileUuid", raProfileUuid)
                .append("csrAttributes", csrAttributes)
                .append("issueAttributes", issueAttributes)
                .append("customAttributes", customAttributes)
                .toString();
    }
}
