package com.czertainly.api.model.core.setting;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
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

    @Schema(description = "List of setting section attributes definitions",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<BaseAttribute> attributeDefinitions = new ArrayList<>();

    @Schema(description = "List of setting section attributes",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ResponseAttributeDto> attributes = new ArrayList<>();

}
