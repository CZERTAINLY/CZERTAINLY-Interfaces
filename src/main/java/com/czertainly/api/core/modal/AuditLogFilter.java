package com.czertainly.api.core.modal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public class AuditLogFilter {
	
	@Schema(
            description = "Author of the action triggered audit log",
            required = true
    )
    private String author;
	
	@Schema(
            description = "Start time of the filter",
            required = true
    )
    private LocalDate createdFrom;
	
	@Schema(
            description = "End time of the filter",
            required = true
    )
    private LocalDate createdTo;
	
	@Schema(
            description = "Status of the filter",
            required = true
    )
    private OperationStatusEnum operationStatus;
	
	@Schema(
            description = "Module triggered the action",
            required = true
    )
    private ObjectType origination;
	
	@Schema(
            description = "Module affected by the action",
            required = true
    )
    private ObjectType affected;
	
	@Schema(
            description = "Identifier of the object created",
            required = true
    )
    private String objectIdentifier;
	
	@Schema(
            description = "Type of the operation",
            required = true
    )
    private OperationType operation;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(LocalDate createdFrom) {
        this.createdFrom = createdFrom;
    }

    public LocalDate getCreatedTo() {
        return createdTo;
    }

    public void setCreatedTo(LocalDate createdTo) {
        this.createdTo = createdTo;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("author", author)
                .append("createdFrom", createdFrom)
                .append("createdTo", createdTo)
                .append("operationStatus", operationStatus)
                .append("originator", origination)
                .append("affected", affected)
                .append("objectIdentifier", objectIdentifier)
                .append("operation", operation)
                .toString();
    }
}
