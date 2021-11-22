package com.czertainly.api.core.modal;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.czertainly.api.model.Identified;
import io.swagger.v3.oas.annotations.media.Schema;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

@JsonPropertyOrder({"id", "uuid", "author", "created", "operationStatus",
        "origination", "affected", "objectIdentifier", "operation", "additionalData"})
public class AuditLogDto implements Identified {
	
	@Schema(
            description = "Audit log Id",
            required = true
    )
    private Long id;
	
	@Schema(
            description = "Audit log UUID",
            required = true
    )
    private String uuid;
	
	@Schema(
            description = "Requestor of the change",
            required = true
    )
    private String author;
	
	@Schema(
            description = "Time when the audit log is registered",
            required = true
    )
    private LocalDateTime created;
	
	@Schema(
            description = "Status of the operation. Either success or failed",
            required = true
    )
    private OperationStatusEnum operationStatus;
	
	@Schema(
            description = "Module triggered the action",
            required = true
    )
    private ObjectType origination;
	
	@Schema(
            description = "Module affected by the operation",
            required = true
    )
    private ObjectType affected;
	
	@Schema(
            description = "Object identifier",
            required = true
    )
    private String objectIdentifier;
	
	@Schema(
            description = "Type of operation performed",
            required = true
    )
    private OperationType operation;
    @JsonRawValue
    private String additionalData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public OperationStatusEnum getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(OperationStatusEnum operationStatus) {
        this.operationStatus = operationStatus;
    }

    public ObjectType getOrigination() {
        return origination;
    }

    public void setOrigination(ObjectType origination) {
        this.origination = origination;
    }

    public ObjectType getAffected() {
        return affected;
    }

    public void setAffected(ObjectType affected) {
        this.affected = affected;
    }

    public String getObjectIdentifier() {
        return objectIdentifier;
    }

    public void setObjectIdentifier(String objectIdentifier) {
        this.objectIdentifier = objectIdentifier;
    }

    public OperationType getOperation() {
        return operation;
    }

    public void setOperation(OperationType operation) {
        this.operation = operation;
    }

    public String getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("uuid", uuid)
                .append("author", author)
                .append("created", created)
                .append("operationStatus", operationStatus)
                .append("originator", origination)
                .append("affected", affected)
                .append("objectIdentifier", objectIdentifier)
                .append("operation", operation)
                .append("additionalData", additionalData)
                .toString();
    }
}
