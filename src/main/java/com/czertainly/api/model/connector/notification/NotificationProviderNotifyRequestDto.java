package com.czertainly.api.model.connector.notification;

import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class NotificationProviderNotifyRequestDto {
    @Schema(description = "Mapping attributes value for recipient", requiredMode = Schema.RequiredMode.REQUIRED,
            type = "object",
            oneOf = {
                    NotificationRecipientDto.class,
                    UserNotificationRecipientDto.class
            })
    private List<NotificationRecipientDto> recipients;

    @Schema(description = "Event name that happened to trigger the notification", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String event;

    @Schema(description = "Resource which is represented by data", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "certificate")
    private Resource resource;

    @Schema(description = "Event name that happened to trigger the notification", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Object notificationData;
}
