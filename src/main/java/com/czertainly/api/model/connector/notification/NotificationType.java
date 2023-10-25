package com.czertainly.api.model.connector.notification;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.czertainly.api.model.connector.notification.data.*;
import com.czertainly.api.model.core.auth.Resource;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum NotificationType implements IPlatformEnum {

    // certificates
    CERTIFICATE_STATUS_CHANGED("certificate_status_changed", "Certificate validation status changed", "Notification when the certificate changes state with detail about the certificate", Resource.CERTIFICATE, NotificationDataCertificateStatusChanged.class),
    CERTIFICATE_ACTION_PERFORMED("certificate_action_performed", "Certificate action performed", "Notification after certificate action (e.g.: issue, renew, rekey, revoke, etc.) was completed with detail about its execution", Resource.CERTIFICATE, NotificationDataCertificateActionPerformed.class),

    // approvals
    APPROVAL_REQUESTED("approval_requested", "Approval requested", "Notification about requesting approval on specific operation included", Resource.APPROVAL, NotificationDataApproval.class),
    APPROVAL_CLOSED("approval_closed", "Approval closed", "Notification after approval was closed informing about the result of approval process", Resource.APPROVAL, NotificationDataApproval.class),

    // scheduler
    SCHEDULED_JOB_COMPLETED("scheduled_job_completed", "Scheduled job completed", "Notification about scheduled job execution finished with result and detail of its execution", Resource.SCHEDULED_JOB, NotificationDataScheduledJobCompleted.class),

    // other
    OTHER("other", "Other", "Uncategorized text notification about operations done in the platform", null, NotificationDataText.class);

    private static final NotificationType[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;
    private final Resource resource;
    private final Class<?> notificationData;

    NotificationType(String code, String label, String description, Resource resource, Class<?> notificationData) {
        this.code = code;
        this.label = label;
        this.description = description;

        this.resource = resource;
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
