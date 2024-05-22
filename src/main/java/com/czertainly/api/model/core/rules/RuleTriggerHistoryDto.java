package com.czertainly.api.model.core.rules;

import com.czertainly.api.model.common.UuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RuleTriggerHistoryDto extends UuidDto {

    @Schema(description = "UUID of the object that the trigger has been evaluated on.")
    private String objectUuid;

    @Schema(description = "UUID of the object that the trigger has been evaluated on.")
    private String referenceObjectUuid;

    @Schema(description = "All conditions in the trigger have been matched.")
    private boolean conditionsMatched;

    @Schema(description = "All actions in the trigger have been performed.")
    private boolean actionsPerformed;

    @Schema(description = "List of records for each action that has not been performed and each condition that has not been evaluated.")
    private List<RuleTriggerHistoryRecordDto> records = new ArrayList<>();

    @Schema(description = "Time at which has the trigger been triggered")
    private LocalDateTime triggeredAt;

    @Schema(description = "Additional message. ")
    private String message;

}
