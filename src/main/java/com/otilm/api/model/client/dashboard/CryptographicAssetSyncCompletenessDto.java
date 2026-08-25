package com.otilm.api.model.client.dashboard;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.Map;
import lombok.Data;

/**
 * Coverage of the cryptographic asset sync across the stored CBOM documents: inventory numbers are exact only over
 * documents whose assets have been synced, and this block is what lets a dashboard say "N of M documents covered".
 */
@Data
@Schema(name = "CryptographicAssetSyncCompletenessDto",
        description = "Coverage of the cryptographic asset sync across stored CBOM documents")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CryptographicAssetSyncCompletenessDto {

    @Schema(description = "CBOM document count by asset sync state. Keys are CbomAssetSyncState codes; every state "
            + "is present, with 0 when none")
    private Map<String, Long> cbomStatBySyncState;

    @Schema(description = "The most recent time any record successfully reached the synced state; absent until the "
            + "first record does")
    private OffsetDateTime lastCompletedSyncAt;
}
