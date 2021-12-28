package com.czertainly.api.model.connector.discovery;

import com.czertainly.api.model.commons.AttributeDefinition;
import com.czertainly.api.model.commons.NameAndUuidDto;
import com.czertainly.api.model.core.discovery.DiscoveryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Map;

public class DiscoveryProviderRequestDto {

    @Schema(description = "Name of the discovery",
            required = true)
    private String name;

    @Schema(description = "Discovery Provider Attributes. Mandatory for creating new Discovery",
            implementation = AttributeDefinition.class
            )
    private List<AttributeDefinition> attributes;

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AttributeDefinition> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDefinition> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("name", name)
                .toString();
    }
}
