package com.czertainly.api.model.core.other;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.czertainly.api.model.core.auth.Resource;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum ResourceEvent implements IPlatformEnum {

    // Certificates
    CERTIFICATE_STATUS_CHANGED("certificateStatusChanged", "Certificate validation status changed", "Notification when the certificate changes state with detail about the certificate", Resource.CERTIFICATE),
    CERTIFICATE_ACTION_PERFORMED("certificateActionPerformed", "Certificate action performed", "Notification after certificate action (e.g.: issue, renew, rekey, revoke, etc.) was completed with detail about its execution", Resource.CERTIFICATE),
    CERTIFICATE_DISCOVERED("certificateDiscovered", "Certificate discovered", "Notification when the certificate changes state with detail about the certificate", Resource.CERTIFICATE, List.of(Resource.DISCOVERY), true),

    // Discoveries
    DISCOVERY_FINISHED("discoveryFinished", "Discovery Finished", "Discovery has been finished.", Resource.DISCOVERY),

    // Approval
    APPROVAL_REQUESTED("approval_requested", "Approval requested", "Notification about requesting approval on specific operation included", Resource.APPROVAL),
    APPROVAL_CLOSED("approval_closed", "Approval closed", "Notification after approval was closed informing about the result of approval process", Resource.APPROVAL),

    // Scheduler
    SCHEDULED_JOB_COMPLETED("scheduled_job_completed", "Scheduled job completed", "Notification about scheduled job execution finished with result and detail of its execution", Resource.SCHEDULED_JOB);

    private static final ResourceEvent[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;
    private final Resource resource;
    private final List<Resource> overridingResources;
    private final boolean allowIgnoreTriggers;

    ResourceEvent(final String code, final String label, final String description, final Resource resource) {
        this(code, label, description, resource, List.of(), false);
    }

    ResourceEvent(final String code, final String label, final String description, final Resource resource, boolean allowIgnoreTriggers) {
        this(code, label, description, resource, List.of(), allowIgnoreTriggers);
    }

    ResourceEvent(final String code, final String label, final String description, final Resource resource, final List<Resource> overridingResources, boolean allowIgnoreTriggers) {
        this.code = code;
        this.label = label;
        this.description =description;
        this.resource = resource;
        this.allowIgnoreTriggers = allowIgnoreTriggers;
        this.overridingResources = overridingResources == null ? List.of() : overridingResources;
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
    public static ResourceEvent findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown Resource event {}", code)));
    }

    public static List<ResourceEvent> listEventsByResource(Resource resource) {
        return Arrays.stream(VALUES).filter(event -> event.resource == resource || event.overridingResources.contains(resource)).toList();
    }

}
