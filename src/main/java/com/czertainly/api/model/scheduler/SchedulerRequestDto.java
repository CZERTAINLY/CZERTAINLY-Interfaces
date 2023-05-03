package com.czertainly.api.model.scheduler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SchedulerRequestDto {

    private SchedulerDetail schedulerDetail;

    public SchedulerRequestDto(SchedulerDetail schedulerDetail) {
        this.schedulerDetail = schedulerDetail;
    }
}
