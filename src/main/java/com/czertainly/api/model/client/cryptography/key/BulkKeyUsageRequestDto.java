package com.czertainly.api.model.client.cryptography.key;

import com.czertainly.api.model.core.cryptography.key.KeyUsage;
import com.czertainly.api.model.core.logging.Loggable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BulkKeyUsageRequestDto implements Loggable {

    @Schema(
            description = "Usages for the Key",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<KeyUsage> usage;

    @Schema(
            description = "Key UUIDs",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<UUID> uuids;

    @Override
    public Serializable toLogData() {
        return null;
    }

    @Override
    public List<String> toLogResourceObjectsNames() {
        return List.of();
    }

    @Override
    public List<UUID> toLogResourceObjectsUuids() {
        return uuids;
    }
}
