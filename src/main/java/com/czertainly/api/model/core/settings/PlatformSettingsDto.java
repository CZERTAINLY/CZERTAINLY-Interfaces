package com.czertainly.api.model.core.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class PlatformSettingsDto implements SettingsDto {

    @Valid
    @Schema(description = "Utils settings of the platform")
    private UtilsSettingsDto utils;

    @Valid
    @Schema(description = "Settings applicable to all certificates in inventory by default")
    private CertificateSettingsDto certificates;

}
