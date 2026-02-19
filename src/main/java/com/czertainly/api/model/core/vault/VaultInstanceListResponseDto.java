package com.czertainly.api.model.core.vault;

import com.czertainly.api.model.common.BasePaginationResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class VaultInstanceListResponseDto extends BasePaginationResponseDto {

    @Schema(description = "List of Vault instances", requiredMode = Schema.RequiredMode.REQUIRED)
    List<VaultInstanceDto> vaultInstances;


}
