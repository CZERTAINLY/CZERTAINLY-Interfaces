package com.czertainly.api.model.core.other;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.czertainly.api.model.common.events.data.*;
import com.czertainly.api.model.core.auth.Resource;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@Schema(enumAsRef = true)
public enum ResourceEvent implements IPlatformEnum {

    // Certificates
    CERTIFICATE_STATUS_CHANGED(Codes.CERTIFICATE_STATUS_CHANGED, "Certificate validation status changed", "Event when the certificate changes validation status with detail about the certificate", Resource.CERTIFICATE, List.of(Resource.RA_PROFILE, Resource.GROUP), CertificateStatusChangedEventData.class),
    CERTIFICATE_ACTION_PERFORMED(Codes.CERTIFICATE_ACTION_PERFORMED, "Certificate action performed", "Event after certificate action (e.g.: issue, renew, rekey, revoke, etc.) was completed with detail about its execution", Resource.CERTIFICATE, List.of(Resource.RA_PROFILE, Resource.GROUP), CertificateActionPerformedEventData.class),
    CERTIFICATE_DISCOVERED(Codes.CERTIFICATE_DISCOVERED, "Certificate discovered", "Event when the certificate has been newly discovered by some discovery", Resource.CERTIFICATE, List.of(Resource.DISCOVERY), CertificateDiscoveredEventData.class),

    // Discoveries
    DISCOVERY_FINISHED(Codes.DISCOVERY_FINISHED, "Discovery Finished", "Event when discovery has been finished.", Resource.DISCOVERY, DiscoveryFinishedEventData.class),

    // Approval
    APPROVAL_REQUESTED(Codes.APPROVAL_REQUESTED, "Approval requested", "Event about requesting approval on specific operation defined by current approval step", Resource.APPROVAL, ApprovalEventData.class),
    APPROVAL_CLOSED(Codes.APPROVAL_CLOSED, "Approval closed", "Event after approval was closed informing about the result of approval process", Resource.APPROVAL, ApprovalEventData.class),

    // Scheduler
    SCHEDULED_JOB_FINISHED(Codes.SCHEDULED_JOB_FINISHED, "Scheduled job finished", "Notification about scheduled job execution finished with result and detail of its execution", Resource.SCHEDULED_JOB, ScheduledJobFinishedEventData.class);

    private static final ResourceEvent[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;
    private final Resource resource;
    private final List<Resource> overridingResources;
    private final Class<? extends EventData> eventData;

    ResourceEvent(final String code, final String label, final String description, final Resource resource, Class<? extends EventData> eventData) {
        this(code, label, description, resource, List.of(), eventData);
    }

    ResourceEvent(final String code, final String label, final String description, final Resource resource, final List<Resource> overridingResources, Class<? extends EventData> eventData) {
        this.code = code;
        this.label = label;
        this.description =description;
        this.resource = resource;
        this.overridingResources = overridingResources == null ? List.of() : overridingResources;
        this.eventData = eventData;
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

    public static class Codes {

        public static final String CERTIFICATE_STATUS_CHANGED = "certificate_status_changed";
        public static final String CERTIFICATE_ACTION_PERFORMED = "certificate_action_performed";
        public static final String CERTIFICATE_DISCOVERED = "certificate_discovered";

        public static final String DISCOVERY_FINISHED = "discovery_finished";

        public static final String APPROVAL_REQUESTED = "approval_requested";
        public static final String APPROVAL_CLOSED = "approval_closed";

        public static final String SCHEDULED_JOB_FINISHED = "scheduled_job_finished";

        private Codes() {

        }
    }

}
