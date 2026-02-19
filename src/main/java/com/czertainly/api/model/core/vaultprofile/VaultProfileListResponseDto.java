package com.czertainly.api.model.core.vaultprofile;

import com.czertainly.api.model.common.BasePaginationResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class VaultProfileListResponseDto extends BasePaginationResponseDto {

    @Schema(description = "List of Vault profiles", requiredMode = Schema.RequiredMode.REQUIRED)
    List<VaultProfileDto> vaultProfiles;
}
