package com.otilm.api.model.core.cryptoasset;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Detail of one inventory asset: the row fields plus verdict provenance, every source CBOM that references the asset
 * with the payload and occurrence evidence recorded from it, and the producer OIDs including refuted ones.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CryptographicAssetDetailDto extends CryptographicAssetDto {

    @Schema(description = "Provenance of the asset's PQC verdict", requiredMode = Schema.RequiredMode.REQUIRED)
    private CryptographicAssetVerdictDto verdict;

    @Schema(description = "Source CBOM documents referencing this asset, with per-source payload and evidence",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CryptographicAssetSourceDto> sources;

    @Schema(description = "Object identifiers producers attached to this asset; may be empty",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CryptographicAssetOidDto> oids;
}
