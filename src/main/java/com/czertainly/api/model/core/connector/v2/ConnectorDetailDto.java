package com.czertainly.api.model.core.connector.v2;

import com.czertainly.api.clients.ApiClientConnectorInfo;
import com.czertainly.api.model.client.attribute.ResponseAttribute;
import com.czertainly.api.model.client.connector.v2.ConnectorInterfaceInfo;
import com.czertainly.api.model.core.connector.AuthType;
import com.czertainly.api.model.core.connector.FunctionGroupDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "ConnectorDetailDtoV2")
public class ConnectorDetailDto extends ConnectorDto implements ApiClientConnectorInfo {

    @Schema(description = "List of Function Groups implemented by the Connector",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<FunctionGroupDto> functionGroups = new ArrayList<>();

    @Schema(description = "List of connector interfaces implemented by the Connector",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ConnectorInterfaceInfo> interfaces = new ArrayList<>();

    @Schema(description = "Type of authentication for the Connector",
            examples = {"none"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private AuthType authType;

    @Schema(description = "List of Attributes for the authentication type",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<ResponseAttribute> authAttributes;

    @Schema(description = "List of Custom Attributes")
    private List<ResponseAttribute> customAttributes;

}
