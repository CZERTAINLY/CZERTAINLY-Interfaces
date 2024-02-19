package com.czertainly.api.model.core.rules;

import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class RuleActionGroupDto {

    @Schema(
            description = "UUID of the Rule Action Group",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String uuid;

    @Schema(
            description = "Name of the Rule Action Group",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @Schema(
            description = "Description of the Rule Action Group"
    )
    private String description;

    @Schema(
            description = "Resource associated with the Rule Action Group",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Resource resource;

    @Schema(
            description = "List of Rule Actions in the Rule Actions Group"
    )
    private List<RuleActionDto> actions;

}
