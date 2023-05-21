package com.czertainly.api.model.scheduler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ScheduledJobHistoryResponseDto extends PaginationResponseDto {

    @Schema(description = "Scheduled job history", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<SchedulerJobHistoryDto> schedulerJobHistoryDtoList;

}
