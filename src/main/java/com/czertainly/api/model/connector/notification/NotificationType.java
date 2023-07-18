package com.czertainly.api.model.connector.notification;

import lombok.Getter;

@Getter
public enum NotificationType {

    TEXT("text", NotificationDataText.class),
    STATUS_CHANGE("status_change", NotificationDataStatusChange.class),
    SCHEDULED_JOB_COMPLETED("scheduled_job_completed", NotificationDataScheduledJobCompleted.class);

    NotificationType(String code, Class<?> notificationData) {
        this.code = code;
        this.notificationData = notificationData;
    }

    private final String code;
    private final Class<?> notificationData;
}
