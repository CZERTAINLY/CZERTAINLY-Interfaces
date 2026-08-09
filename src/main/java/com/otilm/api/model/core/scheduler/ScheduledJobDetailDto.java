package com.otilm.api.model.core.scheduler;

import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScheduledJobDetailDto extends ScheduledJobDto {

    private UUID userUuid;

    private Object objectData;

}
