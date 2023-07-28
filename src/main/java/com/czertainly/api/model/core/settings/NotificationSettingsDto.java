package com.czertainly.api.model.core.settings;

import com.czertainly.api.model.connector.notification.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class NotificationSettingsDto {

    @Schema(
            description = "Type settings of the notification",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Map<NotificationType, String> notificationsMapping;
}
