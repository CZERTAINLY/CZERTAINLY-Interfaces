package com.czertainly.api.model.core.setting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AllSettingsDto {
    @Schema(
            description = "General setting",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private GeneralSettingsDto general;
}
