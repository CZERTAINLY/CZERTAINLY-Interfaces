package com.czertainly.api.model.core.rules;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public class RuleTriggerDetailDto extends RuleTriggerDto {

    @Schema(
            description = "List of Rules in the Rule Trigger",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RuleDto> rules;

    @Schema(
            description = "List of Action Groups in the Rule Trigger",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RuleActionGroupDto> actionGroups;

    @Schema(
            description = "List of Rule Actions in the Rule Trigger",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RuleActionDto> actions;
}
