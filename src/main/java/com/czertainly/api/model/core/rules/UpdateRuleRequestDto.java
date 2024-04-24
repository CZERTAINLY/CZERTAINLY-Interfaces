package com.czertainly.api.model.core.rules;

import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateRuleRequestDto {

    @Schema(
            description = "Description of the Rule"
    )
    private String description;


    @Schema(
            description = "Type of the Resource associated with the Rule"
    )
    private String resourceType;

    @Schema(
            description = "Format of the Resource associated with the Rule"
    )
    private String resourceFormat;


    @Schema(
            description = "List of conditions to add in the Rule"
    )
    private List<RuleConditionRequestDto> conditions = new ArrayList<>();

    @Schema(
            description = "List of UUIDs of existing condition groups to add in the Rule"
    )
    private List<String> conditionGroupsUuids = new ArrayList<>();

}
