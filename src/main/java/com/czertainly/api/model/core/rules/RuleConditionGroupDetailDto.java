package com.czertainly.api.model.core.rules;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RuleConditionGroupDetailDto extends RuleConditionGroupDto {

    @Schema(
            description = "List of the Rule Conditions in the Rule Condition Group",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RuleConditionDto> conditions;

}
