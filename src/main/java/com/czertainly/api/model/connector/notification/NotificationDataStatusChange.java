package com.czertainly.api.model.connector.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationDataStatusChange {
    private String oldStatus;
    private String newStatus;
}
