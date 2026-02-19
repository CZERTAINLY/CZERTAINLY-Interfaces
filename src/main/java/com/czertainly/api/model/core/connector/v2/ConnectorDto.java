package com.czertainly.api.model.core.connector.v2;

import com.czertainly.api.model.client.connector.v2.ConnectorVersion;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.core.connector.ConnectorStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "ConnectorDtoV2")
public class ConnectorDto extends NameAndUuidDto {

    @Schema(description = "Version of the Connector based on the implemented interfaces.",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "v2")
    private ConnectorVersion version;

    @Schema(description = "URL of the Connector",
            examples = {"http://network-discovery-provider:8080"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String url;

    @Schema(description = "Status of the Connector",
            examples = {"CONNECTED"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private ConnectorStatus status;

}
