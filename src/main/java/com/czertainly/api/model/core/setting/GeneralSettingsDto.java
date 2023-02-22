package com.czertainly.api.model.core.setting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GeneralSettingsDto {

    @Schema(
            description = "URL of the Util Service",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "http://util-service:8080"
    )
    private String utilsServiceUrl;
}
