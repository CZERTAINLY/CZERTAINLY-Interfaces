package com.czertainly.api.model.connector.notification;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum NotificationType {

    STATUS_CHANGE("status_change");
    @JsonValue
    private String code;

    NotificationType(String code) {
        this.code = code;
    }

}
