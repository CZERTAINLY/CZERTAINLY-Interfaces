package com.otilm.api.model.connector.notification;

import com.otilm.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import lombok.Data;

@Data
@Schema(description = "Connector-sourced metadata grouped by connector and source object type; same-named attributes from different connectors live in different groups")
public class NotificationMetadataGroupDto {

    @Schema(description = "Name of the connector that produced the metadata",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String connectorName;

    @Schema(description = "Resource type of the source object the metadata originates from",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Resource sourceObjectType;

    @Schema(description = "Metadata attributes keyed by attribute name", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, NotificationAttributeDto> attributes;
}
