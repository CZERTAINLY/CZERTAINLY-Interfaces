package com.otilm.api.model.client.cryptography.key;

import com.otilm.api.model.core.cryptography.key.KeyUsage;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UpdateKeyUsageRequestDto {

    @Schema(description = "Usages for the Key", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<KeyUsage> usage;

    @Schema(description = "List of UUIDs of the key Items. If not provided, the usage will be updated to all the items"
            + "in the key")
    private List<UUID> uuids;
}
