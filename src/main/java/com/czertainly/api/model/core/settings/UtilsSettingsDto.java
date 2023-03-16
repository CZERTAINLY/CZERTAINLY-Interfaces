package com.czertainly.api.model.core.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UtilsSettingsDto {

    @Schema(
            description = "URL of the Util Service",
            example = "http://util-service:8080"
    )
    private String utilsServiceUrl;
}
