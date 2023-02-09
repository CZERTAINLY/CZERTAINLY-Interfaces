package com.czertainly.api.model.core.certificate;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.HashMap;

public class CertificateEventHistoryDto {
    @Schema(description = "UUID of the event", requiredMode = Schema.RequiredMode.REQUIRED)
    private String uuid;

    @Schema(description = "UUID of the Certificate", requiredMode = Schema.RequiredMode.REQUIRED)
    private String certificateUuid;

    @Schema(description = "Event creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime created;

    @Schema(description = "Created By", requiredMode = Schema.RequiredMode.REQUIRED)
    private String createdBy;

    @Schema(description = "Event type", requiredMode = Schema.RequiredMode.REQUIRED)
    private CertificateEvent event;

    @Schema(description = "Event result", requiredMode = Schema.RequiredMode.REQUIRED)
    private CertificateEventStatus status;

    @Schema(description = "Event message", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

    @Schema(description = "Additional information for the event")
    private HashMap<String, Object> additionalInformation;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCertificateUuid() {
        return certificateUuid;
    }

    public void setCertificateUuid(String certificateUuid) {
        this.certificateUuid = certificateUuid;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public CertificateEvent getEvent() {
        return event;
    }

    public void setEvent(CertificateEvent event) {
        this.event = event;
    }

    public CertificateEventStatus getStatus() {
        return status;
    }

    public void setStatus(CertificateEventStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<String, Object> getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(HashMap<String, Object> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
