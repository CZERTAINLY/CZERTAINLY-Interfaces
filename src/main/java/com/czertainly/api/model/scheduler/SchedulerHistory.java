package com.czertainly.api.model.scheduler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SchedulerHistory {

    private Long jobID;

    private SchedulerExecutionStatus status;

    public SchedulerHistory(Long jobID, SchedulerExecutionStatus status) {
        this.jobID = jobID;
        this.status = status;
    }
}
