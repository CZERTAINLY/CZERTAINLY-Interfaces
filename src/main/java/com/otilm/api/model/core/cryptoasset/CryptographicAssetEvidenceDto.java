package com.otilm.api.model.core.cryptoasset;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * One occurrence of an asset as recorded by a producing tool: where in the scanned estate the asset was seen.
 */
@Data
public class CryptographicAssetEvidenceDto {

    @Schema(description = "Where the occurrence was found: a file path, URL or other locator from the source document",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String location;

    @Schema(description = "Line number within the location", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer line;

    @Schema(description = "Offset within the location", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer offset;

    @Schema(description = "Symbol recorded at the occurrence", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String symbol;

    @Schema(description = "Free-text context recorded by the producer", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String additionalContext;
}
