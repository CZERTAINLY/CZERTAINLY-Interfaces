package com.czertainly.api.model.core.scheduler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ScheduledJobDetailDto extends ScheduledJobDto {

    private UUID userUuid;

    private String jobClassName;

    @Schema(
            description = "Is system scheduled job",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean system;
    
    private Object objectData;

}
