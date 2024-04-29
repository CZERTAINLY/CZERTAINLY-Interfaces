package com.czertainly.api.model.core.rules;

import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RuleConditionGroupRequestDto {

    @Schema(
            description = "Name of the Rule Condition Group",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @Schema(
            description = "Description of the Rule Condition Group"
    )
    private String description;

    @Schema(
            description = "Resource associated with the Rule Condition Group",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Resource resource;

    @Schema(
            description = "List of the Rule Conditions to add in the Rule Condition Group",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RuleConditionRequestDto> conditions = new ArrayList<>();

}
