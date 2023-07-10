package com.czertainly.api.model.client.notification;

import com.czertainly.api.model.core.scheduler.PaginationResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class NotificationResponseDto extends PaginationResponseDto {
    @Schema(
            description = "Notifications",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<NotificationDto> items;
}
