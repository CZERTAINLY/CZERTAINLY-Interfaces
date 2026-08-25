package com.otilm.api.model.core.cryptoasset;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * An object identifier a producer attached to an asset. Provably wrong entries are kept rather than dropped — they are
 * the only record that a producer is unreliable — but the default OID and free-text search excludes them; they are
 * reachable only through an explicit opt-in, and this flag is what lets a client label such an entry instead of
 * presenting it as fact.
 */
@Data
public class CryptographicAssetOidDto {

    @Schema(description = "Object identifier as recorded from the producers",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String oid;

    @Schema(description = "True when the platform determined the identifier does not describe this asset",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean refuted;
}
