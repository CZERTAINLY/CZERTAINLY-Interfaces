package com.czertainly.api.model.core.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PlatformSettingsDto {

    @Schema(
            description = "Utils settings of the platform"
    )
    private UtilsSettingsDto utils;
}
