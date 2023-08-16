package com.czertainly.api.model.connector.notification;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.czertainly.api.model.connector.notification.data.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum NotificationType implements IPlatformEnum {

    // certificates
    CERTIFICATE_STATUS_CHANGED("certificate_status_changed", "Certificate status changed", "Notification when the certificate changes state with detail about the certificate", NotificationDataCertificateStatusChanged.class),
    CERTIFICATE_ACTION_PERFORMED("certificate_action_performed", "Certificate action performed", "Notification after certificate action (e.g.: issue, renew, rekey, revoke, etc.) was completed with detail about its execution", NotificationDataCertificateActionPerformed.class),

    // approvals
    APPROVAL_REQUESTED("approval_requested", "Approval requested", "Notification about requesting approval on specific operation included", NotificationDataApproval.class),
    APPROVAL_CLOSED("approval_closed", "Approval closed", "Notification after approval was closed informing about the result of approval process", NotificationDataApproval.class),

    // scheduler
    SCHEDULED_JOB_COMPLETED("scheduled_job_completed", "Scheduled job completed", "Notification about scheduled job execution finished with result and detail of its execution", NotificationDataScheduledJobCompleted.class),

    // other
    OTHER("other", "Other", "Uncategorized text notification about operations done in the platform", NotificationDataText.class);

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

    NotificationType(String code, String label, String description, Class<?> notificationData) {
        this.code = code;
        this.label = label;
        this.description = description;
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
