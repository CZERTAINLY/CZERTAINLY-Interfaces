package com.czertainly.api.model.client.cryptography.key;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BulkCompromiseKeyRequestDto {

    @Schema(
            description = "Usages for the Key",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private KeyCompromiseReason reason;

    @Schema(
            description = "List of UUIDs of the keys. This will mark all the items inside the selected key as compromised"
    )
    private List<UUID> uuids;
}
