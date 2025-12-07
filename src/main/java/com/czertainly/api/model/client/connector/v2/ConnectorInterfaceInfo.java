package com.czertainly.api.model.client.connector.v2;

import com.czertainly.api.model.core.connector.FunctionGroupCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ConnectorInterfaceInfo {

    @Schema(description = "Code of the implemented connector interface", requiredMode = Schema.RequiredMode.REQUIRED)
    private ConnectorInterface code;

    @Schema(description = "Version of the implemented connector interface", requiredMode = Schema.RequiredMode.REQUIRED)
    private String version;

}
