package com.czertainly.api.model.scheduler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SchedulerJobHistoryDto {

    private UUID jobUuid;

    private Date startTime;

    private Date endTime;

    private SchedulerJobExecutionStatus status;

}
