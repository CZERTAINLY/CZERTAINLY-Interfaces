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

    /**
     * The connector interface this vault instance is associated with.
     *
     * <p>
     * The prose lives here and not in {@code @Schema}: ConnectorInterfaceDto is a shared component, and OpenAPI 3.0
     * cannot carry a description beside a {@code $ref} — swagger-core hoists it onto the referenced component,
     * rewriting it for authority, discovery and every other API that references it.
     */
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private ConnectorInterfaceDto connectorInterface;

}
