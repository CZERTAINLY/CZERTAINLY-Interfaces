package com.czertainly.api.model.core.secret;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class SecretUpdateObjectsDto {

    @Schema(description = "UUID of the vault profile where the secret is stored")
    @NotNull
    private UUID sourceVaultProfileUuid;

    @Schema(description = "Secret Groups UUIDs (set to empty list to remove secrets from all groups)")
    private Set<UUID> groupUuids;

    @Schema(description = "Secret owner user UUID (set to empty string to remove owner of the secret)")
    private UUID ownerUuid;
}
