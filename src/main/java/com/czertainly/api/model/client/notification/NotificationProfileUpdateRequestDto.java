package com.czertainly.api.model.client.notification;

import com.czertainly.api.model.core.notification.RecipientType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.Duration;
import java.util.UUID;

@Data
public class NotificationProfileUpdateRequestDto {

    @Schema(description = "Description of the Notification profile",
            examples = {"Detail description of the notification profile"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @Schema(description = "Recipient type of notifications produced by profile",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private RecipientType recipientType;

    @Schema(description = "Recipient UUID of notifications produced by profile",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID recipientUuid;

    @Schema(description = "Notification instance UUID",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID notificationInstanceUuid;

    @Schema(description = "Is notification profile sending internal notifications", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean internalNotification;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "Frequency of repeated notification", requiredMode = Schema.RequiredMode.NOT_REQUIRED, type = "string", format = "duration", example = "P1DT12H")
    private Duration frequency;

    @Positive
    @Schema(description = "Maximum number of repetitions of same notification", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer repetitions;

    @AssertTrue(message = "Recipient UUID is required when recipient type is not Owner or None")
    private boolean isRecipientValid() {
        return ((recipientType == RecipientType.OWNER || recipientType == RecipientType.NONE) && recipientUuid == null) || (recipientType != RecipientType.OWNER && recipientType != RecipientType.NONE && recipientUuid != null);
    }

    @AssertTrue(message = "Cannot send internal notification to recipient of type None")
    private boolean isInternalNotificationPossible() {
        return recipientType == RecipientType.NONE && !internalNotification;
    }

    @AssertTrue(message = "Notification instance and/or internal notification is required")
    private boolean isNotificationInstanceValid() {
        return internalNotification || notificationInstanceUuid != null;
    }
}
