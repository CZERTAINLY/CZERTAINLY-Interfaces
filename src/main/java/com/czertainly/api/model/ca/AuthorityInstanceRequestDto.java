package com.czertainly.api.model.ca;

import com.czertainly.api.model.AttributeDefinition;
import com.czertainly.api.model.Identified;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class AuthorityInstanceRequestDto {

    @Schema(description = "Authority instance name",
            required = true)
    private String name;

    @Schema(description = "List of Authority instance Attributes",
            implementation = List.class,
            required = true)
    private List<AttributeDefinition> attributes;

    @Schema(description = "UUID of Authority instance Connector",
            required = true)
    private String connectorUuid;

    @Schema(description = "Authority instance Type",
            example = "LegacyEjbca, ADCS, etc",
            required = true)
    private String authorityType;

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

    public String getConnectorUuid() {
        return connectorUuid;
    }

    public void setConnectorUuid(String connectorUuid) {
        this.connectorUuid = connectorUuid;
    }

    public String getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(String authorityType) {
        this.authorityType = authorityType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("attributes", attributes)
                .append("connectorUuid", connectorUuid)
                .append("authorityType", authorityType)
                .toString();
    }
}
