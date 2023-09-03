package com.czertainly.api.model.connector.notification.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationDataStatusChanged {
    @Schema(description = "Old status of the object", requiredMode = Schema.RequiredMode.REQUIRED)
    private String oldStatus;

    @Schema(description = "New status of the object", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newStatus;
}
