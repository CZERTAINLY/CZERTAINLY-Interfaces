package com.otilm.api.model.connector.notification;

import com.otilm.api.model.core.auth.Resource;
import com.otilm.api.model.core.other.ResourceEvent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public class NotificationProviderNotifyRequestDto {
    @Schema(description = "List of notification recipients", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<NotificationRecipientDto> recipients;

    /**
     * @deprecated
     */
    @Deprecated(forRemoval = true)
    @Schema(description = "Notification event type that happened to trigger the notification", requiredMode = Schema.RequiredMode.REQUIRED)
    private String eventType;

    @Schema(description = "Event type that happened to trigger the notification", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private ResourceEvent event;

    @Schema(description = "Resource which is represented by data", requiredMode = Schema.RequiredMode.NOT_REQUIRED, examples = {"certificate"})
    private Resource resource;

    @Schema(description = "Data associated with notification event and resource", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Object notificationData;

    // Excluded from toString: bulk object data must not leak into logs or tracing spans on accidental dumps.
    @ToString.Exclude
    @Schema(description = "Additional object data enabled on the notification profile", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private NotificationEventObjectDataDto objectData;
}
