package com.czertainly.api.model.connector.notification;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum NotificationRecipientType {

    USER("user"),
    GROUP("group"),
    ROLE("role");

    @JsonValue
    private String code;

    NotificationRecipientType(String code) {
        this.code = code;
    }

}
