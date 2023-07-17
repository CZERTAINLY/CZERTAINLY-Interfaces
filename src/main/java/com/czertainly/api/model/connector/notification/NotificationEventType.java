package com.czertainly.api.model.connector.notification;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum NotificationEventType {

    STATUS_CHANGE("status_change");
    @JsonValue
    private String code;

    NotificationEventType(String code) {
        this.code = code;
    }

}
