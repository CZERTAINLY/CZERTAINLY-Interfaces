package com.czertainly.api.model.connector.notification.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationDataText {
    @Schema(description = "Notification message", requiredMode = Schema.RequiredMode.REQUIRED)
    private String text;

    @Schema(description = "Notification message detail", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String detail;
}
