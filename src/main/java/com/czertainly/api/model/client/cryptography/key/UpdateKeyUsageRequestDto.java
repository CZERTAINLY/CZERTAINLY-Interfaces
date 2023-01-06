package com.czertainly.api.model.client.cryptography.key;

import com.czertainly.api.model.core.cryptography.key.KeyUsage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UpdateKeyUsageRequestDto {

    @Schema(
            description = "Usages for the Key",
            required = true
    )
    private List<KeyUsage> usage;

    @Schema(
            description = "List of UUIDs of the key Items. If not provided, the usage will be updated to all the items" +
                    "in the key"
    )
    private List<UUID> uuids;
}
