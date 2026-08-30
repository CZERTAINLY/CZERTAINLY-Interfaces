package com.otilm.api.model.core.vault;

import com.otilm.api.model.common.NameAndUuidDto;
import com.otilm.api.model.core.connector.v2.ConnectorInterfaceDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VaultInstanceDto extends NameAndUuidDto {

    @Schema(description = "Description of the Vault instance", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @Schema(description = "Connector associated with this Vault instance", requiredMode = Schema.RequiredMode.REQUIRED)
    private NameAndUuidDto connector;

    // ALL_OF_REF gives the description somewhere to live that is not beside the $ref, so it is not hoisted onto the
    // shared ConnectorInterfaceDto component.
    @Schema(description = "The connector interface this vault instance is associated with; null once the interface "
            + "has been removed from its connector.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true,
            schemaResolution = Schema.SchemaResolution.ALL_OF_REF)
    private ConnectorInterfaceDto connectorInterface;

}
