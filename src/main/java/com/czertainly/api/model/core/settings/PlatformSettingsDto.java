package com.czertainly.api.model.core.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlatformSettingsDto implements SettingsDto {

    @NotNull
    @Schema(description = "Utils settings of the platform", requiredMode = Schema.RequiredMode.REQUIRED)
    private UtilsSettingsDto utils;
}
