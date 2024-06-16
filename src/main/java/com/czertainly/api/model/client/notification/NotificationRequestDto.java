package com.czertainly.api.model.client.notification;

import com.czertainly.api.model.core.scheduler.PaginationRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotificationRequestDto extends PaginationRequestDto {
    @Schema(
            description = "Show only unread notifications",
            defaultValue = "false"
    )
    private boolean unread;
}
