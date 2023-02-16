package com.czertainly.api.model.core.setting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UtilServiceSettingDto extends SettingDto {

    @Schema(
            description = "URL of the Util Service",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String url;
}
