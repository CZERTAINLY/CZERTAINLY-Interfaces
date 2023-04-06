package com.czertainly.api.model.client.scep;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ScepProfileRequestDto extends BaseScepProfileRequestDto {

    @Schema(
            description = "Name of the SCEP Profile",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Profile Name 1"
    )
    private String name;

}
