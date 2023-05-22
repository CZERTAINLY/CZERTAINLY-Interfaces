package com.czertainly.api.model.core.scheduler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SchedulerJobInfoDto {

    private String jobName;

    private String cronExpression;


}
