package com.czertainly.api.model.connector.discovery;

import com.czertainly.api.model.common.AttributeDefinition;
import com.czertainly.api.model.common.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class DiscoveryRequestDto {

    @Schema(description = "Name of the discovery",
            required = true)
    private String name;

    @Schema(description = "Discovery Provider Attributes. Mandatory for creating new Discovery"
            )
    private List<RequestAttributeDto> attributes;

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RequestAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<RequestAttributeDto> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("name", name)
                .toString();
    }
}
