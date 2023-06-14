package com.czertainly.api.model.core.scheduler;

import com.czertainly.api.model.scheduler.SchedulerJobExecutionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ScheduledJobDto {

    @Schema(
            description = "UUID of the scheduled job",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID uuid;

    @Schema(
            description = "Name of the scheduled job",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String jobName;

    @Schema(
            description = "CRON expression representing configuration of pattern how to run scheduled job",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String cronExpression;

    @Schema(
            description = "Status of the scheduled job. True = Enabled, False = Disabled",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean enabled;

    @Schema(
            description = "Is scheduled job triggered only once",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean oneShotOnly;

    @Schema(
            description = "Execution status of last job triggered task",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private SchedulerJobExecutionStatus lastExecutionStatus;

}
