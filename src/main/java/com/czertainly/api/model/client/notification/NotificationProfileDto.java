package com.czertainly.api.model.client.notification;

import com.czertainly.api.model.core.notification.RecipientType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class NotificationProfileDto {

    @Schema(
            description = "UUID of the Notification profile",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String uuid;

    @Schema(description = "Name of the Notification profile",
            examples = {"NotificationProfile1"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Description of the Notification profile",
            examples = {"Detail description of the notification profile"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @Schema(description = "Latest version of the Notification profile",
            examples = {"1"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private int version;

    @Schema(description = "Recipient type of notifications produced by profile",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private RecipientType recipientType;

    @Schema(description = "Recipient UUID of notifications produced by profile",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID recipientUuid;

    @Schema(description = "Notification instance UUID",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID notificationInstanceUuid;

    @Schema(description = "Is notification profile sending internal notifications",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean internalNotification;

}
