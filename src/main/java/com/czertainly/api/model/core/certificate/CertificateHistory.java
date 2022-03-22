package com.czertainly.api.model.core.certificate;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class CertificateHistory {
    @Schema(description = "UUID of the history", required = true)
    private String uuid;

    @Schema(description = "UUID of the Certificate", required = true)
    private String certificateUuid;

    @Schema(description = "Created time", required = true)
    private LocalDateTime created;

    @Schema(description = "Created By", required = true)
    private String createdBy;

    @Schema(description = "Action type", required = true)
    private CertificateAction action;

    @Schema(description = "Status of the action", required = true)
    private CertificateActionStatus status;

    @Schema(description = "Message for the action", required = true)
    private String message;

    @Schema(description = "Additional information for the action")
    private Object additionalInformation;

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

    public CertificateAction getAction() {
        return action;
    }

    public void setAction(CertificateAction action) {
        this.action = action;
    }

    public CertificateActionStatus getStatus() {
        return status;
    }

    public void setStatus(CertificateActionStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(Object additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
