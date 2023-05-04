package com.czertainly.api.model.scheduler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SchedulerJobExecutionMessage {

    @JsonProperty
    private Long jobID;

    @JsonProperty
    private String jobName;

    @JsonProperty
    private String classToBeExecuted;

    public SchedulerJobExecutionMessage(final Long jobID, final String jobName, final String classToBeExecuted) {
        this.jobID = jobID;
        this.jobName = jobName;
        this.classToBeExecuted = classToBeExecuted;
    }

    @Override
    public String toString() {
        return super.getClass().getName()
                + "(jobID=" + jobID
                + ", jobName=" + jobName
                + ", classToBeExecuted=" + classToBeExecuted
                + ")";
    }
}
