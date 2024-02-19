package com.czertainly.api.model.core.rules;

import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RuleTriggerDto {

    @Schema(
            description = "UUID of the Rule Trigger",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String uuid;

    @Schema(
            description = "Type of the Rule Trigger",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private RuleTriggerType triggerType;

    @Schema(
            description = "Name of the event of the Rule Trigger",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String eventName;

    @Schema(
            description = "Resource associated with the Rule Trigger",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Resource resource;

    @Schema(
            description = "UUID of the Resource associated with the Rule Trigger"
    )
    private UUID resourceUuid;


    @Schema(
            description = "List of Rules in the Rule Trigger"
    )
    private List<RuleDto> rules;

    @Schema(
            description = "List of Action Groups in the Rule Trigger"
    )
    private List<RuleActionGroupDto> actionGroups;

    @Schema(
            description = "List of Rule Actions in the Rule Trigger"
    )
    private List<RuleActionDto> actions;
}
