package com.czertainly.api.model.core.scheduler;

import com.czertainly.api.model.scheduler.SchedulerJobDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SchedulerRequestDto {

    private SchedulerJobDto schedulerDetail;

    public SchedulerRequestDto(SchedulerJobDto schedulerDetail) {
        this.schedulerDetail = schedulerDetail;
    }
}
