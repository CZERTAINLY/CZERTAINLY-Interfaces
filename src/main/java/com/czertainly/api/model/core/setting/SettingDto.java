package com.czertainly.api.model.core.setting;

import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SettingDto extends NameAndUuidDto {
    @Schema(
            description = "Setting section",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Section section;

    @Schema(
            description = "Setting description"
    )
    private String description;
}
