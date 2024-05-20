package com.czertainly.api.model.core.rules;

import com.czertainly.api.model.common.UuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class RuleTriggerHistoryRecordDto extends UuidDto {

    @Schema(name = "UUID of the condition")
    private String ruleConditionUuid;

    @Schema(name = "UUID of the action")
    private String ruleActionUuid;

    @Schema(name = "Message with cause of action/condition failure.")
    private String message;
}
