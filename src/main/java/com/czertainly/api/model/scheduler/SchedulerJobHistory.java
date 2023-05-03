package com.czertainly.api.model.scheduler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SchedulerJobHistory {

    private Long jobID;

    private SchedulerJobExecutionStatus status;

    public SchedulerJobHistory(Long jobID, SchedulerJobExecutionStatus status) {
        this.jobID = jobID;
        this.status = status;
    }
}
