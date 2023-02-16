package com.czertainly.api.model.client.setting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UtilServiceSettingRequestDto {

    @Schema(
            description = "URL of the Util Service",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String url;
}
