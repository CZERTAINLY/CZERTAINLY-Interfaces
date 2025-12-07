package com.czertainly.api.model.client.connector.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ConnectorInfo {

    @Schema(description = "Unique identifier of the connector", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "Name of the connector", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Version of the connector", requiredMode = Schema.RequiredMode.REQUIRED)
    private String version;

    @Schema(description = "Metadata of the connector", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private ConnectorMetadata metadata;

    @Schema(description = "Interfaces supported and implemented by the connector", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ConnectorInterfaceInfo> interfaces = new ArrayList<>();

}
