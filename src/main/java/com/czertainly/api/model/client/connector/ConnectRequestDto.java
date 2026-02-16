package com.czertainly.api.model.client.connector;

import com.czertainly.api.model.client.attribute.RequestAttribute;
import com.czertainly.api.model.core.connector.AuthType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ConnectRequestDto {

    @Schema(description = "URL of the Connector to connect",
            examples = {"http://network-discovery-provicer:8080"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String url;

    @Schema(description = "UUID of the Connector. Mandatory if connection is needed for the same Connector")
    private String uuid;

    @Schema(description = "Type of authentication for the Connector",
            examples = {"none"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private AuthType authType;

    @Schema(description = "List of authentication Attributes. Required if the authentication type is not NONE",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RequestAttribute> authAttributes;

}
