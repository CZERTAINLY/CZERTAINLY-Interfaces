package com.czertainly.api.model.core.rules;

import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RuleTriggerRequestDto {

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
            description = "List of new Rules to add in the Rule Trigger"
    )
    private List<RuleRequestDto> rules;

    @Schema(
            description = "List of UUIDs of existing Rules to add in the Rule Trigger"
    )
    private List<String> rulesUuids;

    @Schema(
            description = "List of new Action Groups to add in the Rule Trigger"
    )
    private List<RuleActionGroupRequestDto> actionGroups;

    @Schema(
            description = "List of UUIDs of existing Action Groups to add in the Rule Trigger"
    )
    private List<RuleActionGroupRequestDto> actionGroupsUuids;

    @Schema(
            description = "List of new Rule Actions to add in the Rule Trigger"
    )
    private List<RuleActionRequestDto> actions;
}
