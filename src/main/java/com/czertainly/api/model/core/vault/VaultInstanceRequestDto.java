package com.czertainly.api.model.core.vault;

import com.czertainly.api.model.client.attribute.RequestAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class VaultInstanceRequestDto {

    @Schema(description = "UUID of the Connector associated with this Vault instance", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID connectorUuid;

    @Schema(description = "UUID of the Connector Interface associated with this Vault instance", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID interfaceUuid;

    @Schema(description = "Name of the Vault instance", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Description of the Vault instance", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @Schema(description = "List of attributes of the Vault instance", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<RequestAttribute> attributes;

    @Schema(description = "List of custom attributes of the Vault instance", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RequestAttribute> customAttributes;
}
