package com.czertainly.api.model.scheduler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SchedulerJobDetailDto {

    private UUID jobUuid;

    private String jobName;

    private String cronExpression;

    private Object objectData;

    private UUID userUuid;

    private boolean enabled;

    private boolean oneShotOnly;

    private boolean system;

    private String jobClassName;


}
