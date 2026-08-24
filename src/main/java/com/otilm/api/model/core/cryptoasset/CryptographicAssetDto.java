package com.otilm.api.model.core.cryptoasset;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Data;

/**
 * One row of the cross-CBOM cryptographic asset inventory. A row is a deduplicated asset, not a component: the same
 * algorithm found in many documents is one row, with the references counted on it.
 */
@Data
public class CryptographicAssetDto {

    @Schema(description = "UUID of the inventory asset", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID uuid;

    @Schema(description = "Normalized display name of the asset", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Type of the asset", requiredMode = Schema.RequiredMode.REQUIRED)
    private CryptographicAssetType type;

    @Schema(description = "Post-quantum readiness verdict computed by the platform rule set",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private PqcVerdict pqcVerdict;

    @Schema(description = "Number of CBOM documents that reference this asset",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private int sourceCbomCount;

    @Schema(description = "Total number of occurrence evidence entries recorded across all source CBOMs",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private long occurrenceCount;
}
