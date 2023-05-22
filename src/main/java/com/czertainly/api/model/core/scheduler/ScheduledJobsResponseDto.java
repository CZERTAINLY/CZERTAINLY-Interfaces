package com.czertainly.api.model.core.scheduler;

import com.czertainly.api.model.scheduler.SchedulerJobDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ScheduledJobsResponseDto extends PaginationResponseDto {

    @Schema(description = "Scheduled jobs", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<SchedulerJobDto> scheduledJobs;

}
