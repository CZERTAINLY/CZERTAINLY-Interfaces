package com.czertainly.api.model.connector.entity;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class GenerateCsrRequestDto {

    @Schema(
            description = "List of Location Attributes",
            required = true
    )
    private List<RequestAttributeDto> locationAttributes;

    @Schema(
            description = "List of Attributes to generate CSR",
            required = true
    )
    private List<RequestAttributeDto> csrAttributes;

    @Schema(description = "Is the request for renewal of Certificate", required = true)
    private Boolean renewal;

    public List<RequestAttributeDto> getLocationAttributes() {
        return locationAttributes;
    }

    public void setLocationAttributes(List<RequestAttributeDto> locationAttributes) {
        this.locationAttributes = locationAttributes;
    }

    public List<RequestAttributeDto> getCsrAttributes() {
        return csrAttributes;
    }

    public void setCsrAttributes(List<RequestAttributeDto> csrAttributes) {
        this.csrAttributes = csrAttributes;
    }

    public Boolean isRenewal() {
        return renewal;
    }

    public void setRenewal(Boolean renewal) {
        this.renewal = renewal;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("locationAttributes", locationAttributes)
                .append("csrAttributes", csrAttributes)
                .append("isRenewalRequest", renewal)
                .toString();
    }
}
