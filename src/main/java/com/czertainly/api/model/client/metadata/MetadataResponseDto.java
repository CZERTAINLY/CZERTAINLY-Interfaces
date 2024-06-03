package com.czertainly.api.model.client.metadata;

import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@Data
@Schema(description = "Metadata response attributes with their source connector")
public class MetadataResponseDto {
    @Schema(description = "UUID of the Connector", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String connectorUuid;

    @Schema(description = "Name of the Connector", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String connectorName;

    @Schema(description = "Source Object Type", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Resource sourceObjectType;

    @Schema(description = "List of Metadata", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ResponseMetadataDto> items;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("connectorUuid", connectorUuid)
                .append("connectorName", connectorName)
                .append("sourceObjectType", sourceObjectType)
                .append("metadata", items)
                .toString();
    }
}
