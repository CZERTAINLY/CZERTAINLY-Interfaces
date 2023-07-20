package com.czertainly.api.model.core.notification;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AttributeMappingDto {

    @Schema(description = "Mapping Attribute UUID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mappingAttributeUuid;

    @Schema(description = "Mapping Attribute Name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mappingAttributeName;

    @Schema(description = "Custom Attribute Uuid", requiredMode = Schema.RequiredMode.REQUIRED)
    private String customAttributeUuid;

}
