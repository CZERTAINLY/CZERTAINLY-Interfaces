package com.czertainly.api.model.core.scheduler;

import com.czertainly.api.model.scheduler.SchedulerJobExecutionStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ScheduledJobHistoryDto {

    private UUID jobUuid;

    private Date startTime;

    private Date endTime;

    private SchedulerJobExecutionStatus status;

}
