package com.czertainly.api.model.core.oid;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomOidEntryUpdateRequestDto {

    @Schema(description = "Display name of the custom OID entry", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 30)
    @NotBlank
    private String displayName;

    @Schema(description = "Description of the custom OID entry", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @Schema(description = "Additional properties depending on OID category", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Valid
    private AdditionalOidPropertiesDto additionalProperties;
}
