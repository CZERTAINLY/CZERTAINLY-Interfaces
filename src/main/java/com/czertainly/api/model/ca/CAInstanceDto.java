package com.czertainly.api.model.ca;

import com.czertainly.api.model.AttributeDefinition;
import com.czertainly.api.model.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class CAInstanceDto extends NameAndUuidDto {

    @Schema(description = "List of Authority Attributes",
            required = true)
    private List<AttributeDefinition> attributes;

    @Schema(description = "Status of the Authority",
            required = true)
    private String status;

    @Schema(description = "UUID of CA Connector",
            required = true)
    private String connectorUuid;

    @Schema(description = "Name of CA Connector",
            required = true)
    private String connectorName;

    @Schema(description = "Authority Type",
            example = "Ejbca, AdCs, etc",
            required = true)
    private String authorityType;

    public List<AttributeDefinition> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDefinition> attributes) {
        this.attributes = attributes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getConnectorName() { return connectorName; }

    public void setConnectorName(String connectorName) { this.connectorName = connectorName; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("attributes", attributes)
                .append("status", status)
                .append("connectorUuid", connectorUuid)
                .append("connectorName", connectorName)
                .append("authorityType", authorityType)
                .toString();
    }
}
