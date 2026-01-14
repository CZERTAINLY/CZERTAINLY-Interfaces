package com.czertainly.api.model.client.connector.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ConnectorInterfaceInfo {

    @Schema(description = "Code of the implemented connector interface", requiredMode = Schema.RequiredMode.REQUIRED)
    private ConnectorInterface code;

    @Schema(description = "Version of the implemented connector interface", requiredMode = Schema.RequiredMode.REQUIRED)
    private String version;

    @Schema(description = "Features supported by the connector interface", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<FeatureFlag> features;

}
