package com.czertainly.api.model.core.connector.v2;

import com.czertainly.api.model.client.connector.v2.ConnectorInfo;
import com.czertainly.api.model.client.connector.v2.ConnectorInterfaceInfo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class ConnectInfoV2 extends ConnectInfo {

    @Schema(description = "Connector Information", requiredMode = Schema.RequiredMode.REQUIRED)
    private ConnectorInfo connector;

    @Schema(description = "Interfaces supported and implemented by the connector", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ConnectorInterfaceInfo> interfaces;


}
