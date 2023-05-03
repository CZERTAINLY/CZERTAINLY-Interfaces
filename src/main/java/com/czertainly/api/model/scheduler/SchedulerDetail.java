package com.czertainly.api.model.scheduler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SchedulerDetail {

    private String jobName;

    private String cronExpression;

    private String classNameToBeExecuted;

    public SchedulerDetail(String jobName, String cronExpression, String classNameToBeExecuted) {
        this.jobName = jobName;
        this.cronExpression = cronExpression;
        this.classNameToBeExecuted = classNameToBeExecuted;
    }

}
