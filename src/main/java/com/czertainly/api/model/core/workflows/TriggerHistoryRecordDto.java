package com.czertainly.api.model.core.workflows;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TriggerHistoryRecordDto {
    @Schema(name = "Message with cause of action/condition failure.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

    @Schema(name = "Condition that is referenced by history record")
    private ConditionDto condition;

    @Schema(name = "Condition that is referenced by history record")
    private ExecutionDto execution;
}
