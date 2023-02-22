package com.czertainly.api.model.core.setting;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SectionSettingsDto extends NameAndUuidDto {
    @Schema(
            description = "Setting section",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Section section;

    @Schema(
            description = "Description of Settings section"
    )
    private String description;

    @Schema(description = "List of RA Profiles attributes",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ResponseAttributeDto> attributes = new ArrayList<>();

}
