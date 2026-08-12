package com.otilm.api.model.client.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.otilm.api.model.client.notification.validation.ValidFrequency;
import com.otilm.api.model.core.notification.NotificationDataCategory;
import com.otilm.api.model.core.notification.RecipientType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class NotificationProfileUpdateRequestDto {

    @Schema(description = "Description of the Notification profile",
            examples = {"Detail description of the notification profile"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @NotNull(message = "Recipient type is required")
    @Schema(description = "Recipient type of notifications produced by profile",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private RecipientType recipientType;

    @Schema(description = "Recipient UUIDs of notifications produced by profile",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<UUID> recipientUuids;

    @Schema(description = "Notification instance UUID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID notificationInstanceUuid;

    @Schema(description = "Is notification profile sending internal notifications",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean internalNotification;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ValidFrequency
    @Schema(description = "Frequency of repeated notification", requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "string", format = "duration", example = "P1DT12H")
    private Duration frequency;

    @Positive
    @Schema(description = "Maximum number of repetitions of same notification",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer repetitions;

    @Schema(description = "Notification data categories included in external notifications sent by this profile. "
            + "When updating, an absent field keeps the current value and an empty list disables enrichment",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<@NotNull NotificationDataCategory> eventDataCategories;

    @AssertTrue(message = "Recipient UUID is required when recipient type is not Owner, None, Default or Object")
    private boolean isRecipientValid() {
        boolean noRecipientUuids = recipientUuids == null || recipientUuids.isEmpty();
        boolean typeWithoutRecipientUuids = recipientType == RecipientType.OWNER || recipientType == RecipientType.NONE
                || recipientType == RecipientType.DEFAULT || recipientType == RecipientType.OBJECT;
        return typeWithoutRecipientUuids == noRecipientUuids;
    }

    @AssertFalse(message = "Cannot send internal notification to recipient of type None, Default or Object")
    private boolean isInternalNotificationPossible() {
        return (recipientType == RecipientType.NONE || recipientType == RecipientType.DEFAULT
                || recipientType == RecipientType.OBJECT) && internalNotification;
    }

    @AssertTrue(message = "Notification instance and/or internal notification is required")
    private boolean isNotificationInstanceValid() {
        return internalNotification || notificationInstanceUuid != null;
    }
}
