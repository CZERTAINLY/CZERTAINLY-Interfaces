package com.czertainly.api.model.core.scheduler;

import com.czertainly.api.model.client.discovery.DiscoveryDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleDiscoveryDto {

    private String jobName;

    private String cronExpression;

    private boolean oneTime;

    private DiscoveryDto request;

}
