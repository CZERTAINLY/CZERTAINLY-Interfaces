package com.czertainly.api.model.core.vault;

import com.czertainly.api.model.common.NameAndUuidDto;
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

}
