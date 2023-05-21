package com.czertainly.api.model.scheduler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ScheduledJobsResponseDto extends PaginationResponseDto {

    @Schema(description = "Scheduled jobs", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<SchedulerJobDto> schedulerJobDtoList;

}
