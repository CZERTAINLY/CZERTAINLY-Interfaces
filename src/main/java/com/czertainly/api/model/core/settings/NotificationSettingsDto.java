package com.czertainly.api.model.core.settings;

import com.czertainly.api.model.connector.notification.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
public class NotificationSettingsDto implements SettingsDto {

    @NotNull
    @Schema(
            description = "Type settings of the notification",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Map<NotificationType, String> notificationsMapping;
}
