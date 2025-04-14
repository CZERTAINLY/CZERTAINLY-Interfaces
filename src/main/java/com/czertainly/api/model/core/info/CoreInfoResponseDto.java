package com.czertainly.api.model.core.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CoreInfoResponseDto {

    @Schema(description = "CZERTAINLY Application information", requiredMode = Schema.RequiredMode.REQUIRED)
    private AppInfoDto app;

    @Schema(description = "Database information", requiredMode = Schema.RequiredMode.REQUIRED)
    private DatabaseInfoDto db;

}
