package com.czertainly.api.model.core.rules;

import com.czertainly.api.model.common.UuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
public class RuleTriggerHistoryRecordDto {
    @Schema(name = "Message with cause of action/condition failure.")
    private String message;

    @Schema(name = "Condition that is referenced by history record", nullable = true)
    private RuleConditionDto condition;

    @Schema(name = "Condition that is referenced by history record", nullable = true)
    private RuleActionDto action;
}
