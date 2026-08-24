package com.otilm.api.model.core.cryptoasset;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Data;

/**
 * Provenance of an asset's PQC verdict. The verdict value itself is the {@code pqcVerdict} carried on the asset row;
 * this block records which rule-set version produced it, when, and why, so a verdict can be re-examined when the rule
 * set moves on.
 */
@Data
public class CryptographicAssetVerdictDto {

    @Schema(description = "Version of the platform rule set that produced the verdict",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String ruleSetVersion;

    @Schema(description = "When the verdict was last evaluated", requiredMode = Schema.RequiredMode.REQUIRED)
    private OffsetDateTime evaluatedAt;

    @Schema(description = "Rule findings behind the verdict, one entry per failed rule; absent when no rule failed",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<String> reasons;
}
