package com.otilm.api.model.scheduler;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SchedulerResponseDto {

    private SchedulerStatus schedulerStatus;

    private String schedulerName;

    private List<SchedulerJobDto> schedulerJobList;

    public SchedulerResponseDto(SchedulerStatus schedulerStatus) {
        this.schedulerStatus = schedulerStatus;
    }

    public SchedulerResponseDto(final SchedulerStatus schedulerStatus, final String schedulerName) {
        this.schedulerStatus = schedulerStatus;
        this.schedulerName = schedulerName;
    }
}
