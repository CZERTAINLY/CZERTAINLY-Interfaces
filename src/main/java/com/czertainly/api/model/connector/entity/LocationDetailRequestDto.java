package com.czertainly.api.model.connector.entity;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class LocationDetailRequestDto {

    @Schema(
            description = "List of Location Attributes",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RequestAttributeDto> locationAttributes;

    public List<RequestAttributeDto> getLocationAttributes() {
        return locationAttributes;
    }

    public void setLocationAttributes(List<RequestAttributeDto> locationAttributes) {
        this.locationAttributes = locationAttributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("locationAttributes", locationAttributes)
                .toString();
    }
}
