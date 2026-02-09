package com.czertainly.api.model.core.secret;

import com.czertainly.api.model.common.BasePaginationResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SecretListResponseDto extends BasePaginationResponseDto {

    @Schema(description = "List of secrets matching the search criteria", requiredMode = Schema.RequiredMode.REQUIRED)
    List<SecretDto> secrets;
}
