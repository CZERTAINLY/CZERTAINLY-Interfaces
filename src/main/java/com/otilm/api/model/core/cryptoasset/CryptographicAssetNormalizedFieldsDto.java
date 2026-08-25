package com.otilm.api.model.core.cryptoasset;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * The normalized, filterable properties the platform derived for an asset. Populated wherever derivable on every asset
 * type, not only algorithms — these are the columns the inventory filters on, so an absent member means "not
 * derivable", never "not applicable to this type".
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CryptographicAssetNormalizedFieldsDto {

    @Schema(description = "Normalized algorithm family", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String algorithmFamily;

    @Schema(description = "Normalized primitive", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String primitive;

    @Schema(description = "Normalized parameter set", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String parameterSet;

    @Schema(description = "Normalized curve", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String curve;

    @Schema(description = "Normalized mode of operation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String mode;

    @Schema(description = "Normalized padding", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String padding;

    @Schema(description = "Normalized variant", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String variant;
}
