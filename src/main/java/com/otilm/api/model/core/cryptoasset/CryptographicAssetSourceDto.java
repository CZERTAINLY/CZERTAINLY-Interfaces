package com.otilm.api.model.core.cryptoasset;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Data;

/**
 * One source CBOM document referencing an inventory asset. The payload keeps what that source declared rather than a
 * normalized projection, because sources disagree and the disagreement is the evidence an operator traces.
 */
@Data
public class CryptographicAssetSourceDto {

    @Schema(description = "UUID of the source CBOM record", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID cbomUuid;

    @Schema(description = "Serial number (URN) of the source CBOM", requiredMode = Schema.RequiredMode.REQUIRED)
    private String serialNumber;

    @Schema(description = "Version of the source CBOM", requiredMode = Schema.RequiredMode.REQUIRED)
    private int version;

    @Schema(description = "Tool or scan that produced the source CBOM (e.g.: CBOM-Lens)",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String source;

    @Schema(description = "Cryptographic properties of the component as this source declared them. Keys follow the "
            + "source document's CycloneDX version, so they may differ between sources; values classified as secret "
            + "material are stored redacted and served redacted here.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, Object> payload;

    @Schema(description = "Occurrence evidence recorded from this source; may be empty",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<CryptographicAssetEvidenceDto> evidence;
}
