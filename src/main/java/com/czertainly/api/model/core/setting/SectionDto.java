package com.czertainly.api.model.core.setting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SectionDto {
    @Schema(
            description = "Setting section",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Section section;

    @Schema(
            description = "Setting section display name",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @Schema(
            description = "Setting section description",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String description;
}
