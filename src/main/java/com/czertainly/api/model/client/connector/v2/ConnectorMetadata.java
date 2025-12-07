package com.czertainly.api.model.client.connector.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ConnectorMetadata {

    @Schema(description = "Description of the connector", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @Schema(description = "Author of the connector", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String author;

    @Schema(description = "License of the connector", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "MIT")
    private String license;

}
