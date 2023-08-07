package com.czertainly.api.model.connector.notification;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.czertainly.api.model.common.enums.cryptography.KeyType;
import com.czertainly.api.model.connector.notification.data.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum NotificationType implements IPlatformEnum {

    TEXT("text", "Text message", NotificationDataText.class),
    SCHEDULED_JOB_COMPLETED("scheduled_job_completed", "Scheduled job Completed", NotificationDataScheduledJobCompleted.class),

    // certificates
    CERTIFICATE_STATUS_CHANGED("certificate_status_changed", "Certificate status change", NotificationDataCertificateStatusChanged.class),
    CERTIFICATE_ACTION_PERFORMED("certificate_action_performed", "Certificate action performed", NotificationDataCertificateActionPerformed.class),

    // approvals
    APPROVAL_REQUESTED("approval_requested", "Approval requested", NotificationDataApproval.class),
    APPROVAL_CLOSED("approval_closed", "Approval closed", NotificationDataApproval.class);

    private static final NotificationType[] VALUES;

    static {
        VALUES = values();
    }

    @Schema(description = "Key type code",
            example = "Secret", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String code;
    private final String label;
    private final String description;
    private final Class<?> notificationData;

    NotificationType(String code, String label, Class<?> notificationData) {
        this.code = code;
        this.label = label;
        this.description = null;
        this.notificationData = notificationData;
    }

    @Override
    @JsonValue
    public String getCode() {
        return this.code;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @JsonCreator
    public static NotificationType findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown notification type code {}", code)));
    }
}
