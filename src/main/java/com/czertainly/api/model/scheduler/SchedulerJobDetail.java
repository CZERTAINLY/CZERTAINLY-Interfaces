package com.czertainly.api.model.scheduler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SchedulerJobDetail {

    private String jobName;

    private String cronExpression;

    private String classNameToBeExecuted;

    public SchedulerJobDetail(String jobName, String cronExpression, String classNameToBeExecuted) {
        this.jobName = jobName;
        this.cronExpression = cronExpression;
        this.classNameToBeExecuted = classNameToBeExecuted;
    }

}
