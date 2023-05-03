package com.czertainly.api.model.scheduler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SchedulerRequestDto {

    private SchedulerJobDetail schedulerDetail;

    public SchedulerRequestDto(SchedulerJobDetail schedulerDetail) {
        this.schedulerDetail = schedulerDetail;
    }
}
