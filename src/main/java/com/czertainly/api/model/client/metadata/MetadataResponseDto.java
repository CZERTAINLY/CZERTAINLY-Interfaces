package com.czertainly.api.model.client.metadata;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@Schema(description = "Metadata response attributes with their source connector")
public class MetadataResponseDto {
    @Schema(description = "UUID of the Connector", requiredMode = Schema.RequiredMode.REQUIRED)
    private String connectorUuid;

    @Schema(description = "Name of the Connector", requiredMode = Schema.RequiredMode.REQUIRED)
    private String connectorName;

    @Schema(description = "List of Metadata", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ResponseMetadataDto> items;

    public String getConnectorUuid() {
        return connectorUuid;
    }

    public void setConnectorUuid(String connectorUuid) {
        this.connectorUuid = connectorUuid;
    }

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public List<ResponseMetadataDto> getItems() {
        return items;
    }

    public void setItems(List<ResponseMetadataDto> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("connectorUuid", connectorUuid)
                .append("connectorName", connectorName)
                .append("metadata", items)
                .toString();
    }
}
