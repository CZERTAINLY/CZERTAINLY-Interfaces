package com.czertainly.api.model.client.notification;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class NotificationDto {
    @Schema(description = "Notification UUID", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID uuid;

    @Schema(description = "Notification message", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

    @Schema(description = "Notification message detail")
    private String detail;

    @Schema(description = "Notification read date")
    private Date readAt;
}
