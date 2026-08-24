package com.otilm.api.model.client.dashboard;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import lombok.Data;

/**
 * Statistics for the cross-CBOM cryptographic asset inventory dashboard. Every count is computed over deduplicated
 * inventory assets, not raw CBOM components, so an asset referenced by many documents counts once. The
 * sync-completeness block states how much of the stored estate the numbers cover — without it a partially synced estate
 * would present partial numbers as the whole truth.
 */
@Data
@Schema(name = "CryptographicAssetStatisticsDto", description = "Cryptographic asset inventory dashboard statistics")
public class CryptographicAssetStatisticsDto {

    @Schema(description = "Total number of deduplicated cryptographic assets in the inventory")
    private Long totalAssets;

    @Schema(description = "Number of CBOM documents contributing assets to the inventory")
    private Long sourceCbomCount;

    @Schema(description = "Asset count by asset type. Keys are CryptographicAssetType codes; every type is present, "
            + "with 0 when none")
    private Map<String, Long> statByType;

    @Schema(description = "Asset count by PQC readiness verdict. Keys are PqcVerdict codes; every verdict is "
            + "present, with 0 when none")
    private Map<String, Long> statByPqcVerdict;

    @Schema(description = "Asset count by algorithm family, limited to the top-N families by count. Use "
            + "distinctAlgorithmFamilyCount for the total; assets with no family are grouped under \"Unassigned\"")
    private Map<String, Long> statByAlgorithmFamily;

    @Schema(description = "Number of distinct algorithm families across the inventory. Lets the client render a "
            + "\"+k more\" overflow beyond the top-N entries in statByAlgorithmFamily")
    private Long distinctAlgorithmFamilyCount;

    @Schema(description = "How completely the inventory reflects the stored CBOM documents")
    private CryptographicAssetSyncCompletenessDto syncCompleteness;
}
