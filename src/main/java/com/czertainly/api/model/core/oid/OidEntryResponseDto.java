package com.czertainly.api.model.core.oid;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OidEntryResponseDto {

    @Schema(description = "Object Identifier (OID) in dot notation", requiredMode = Schema.RequiredMode.REQUIRED)
    private String oid;

    @Schema(description = "Display name of the OID entry", requiredMode = Schema.RequiredMode.REQUIRED)
    private String displayName;

    @Schema(description = "Description of the OID entry", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @Schema(description = "Category of the OID entry", requiredMode = Schema.RequiredMode.REQUIRED)
    private String category;
}
