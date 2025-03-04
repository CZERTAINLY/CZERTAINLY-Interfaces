package com.czertainly.api.model.core.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlatformSettingsDto implements SettingsDto {

    @Valid
    @NotNull
    @Schema(description = "Utils settings of the platform", requiredMode = Schema.RequiredMode.REQUIRED)
    private UtilsSettingsDto utils;

    @Valid
    @Schema(description = "Settings applicable to all certificates in inventory")
    private CertificateSettingsDto certificateSettingsDto;

}
