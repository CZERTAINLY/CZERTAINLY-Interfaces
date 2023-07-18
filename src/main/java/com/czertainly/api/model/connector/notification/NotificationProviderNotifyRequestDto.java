package com.czertainly.api.model.connector.notification;

import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class NotificationProviderNotifyRequestDto {
    @Schema(description = "List of notification recipients", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<NotificationRecipientDto> recipients;

    @Schema(description = "Event type that happened to trigger the notification", requiredMode = Schema.RequiredMode.REQUIRED)
    private NotificationType eventType;

    @Schema(description = "Resource which is represented by data", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "certificate")
    private Resource resource;

    @Schema(description = "Data associated with notification event and resource", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Object notificationData;
}
