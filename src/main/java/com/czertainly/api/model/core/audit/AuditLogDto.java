package com.czertainly.api.model.core.audit;

import com.czertainly.api.model.common.Identified;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRawValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.OffsetDateTime;

@JsonPropertyOrder({"id", "uuid", "author", "created", "operationStatus",
        "origination", "affected", "objectIdentifier", "operation", "additionalData"})
public class AuditLogDto implements Identified {
	
	@Schema(
            description = "Audit log Id",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long id;
	
	@Schema(
            description = "Audit log UUID",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String uuid;
	
	@Schema(
            description = "Requestor of the change",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String author;
	
	@Schema(
            description = "Time when the audit log is registered",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private OffsetDateTime created;
	
	@Schema(
            description = "Status of the operation. Either success or failed",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private OperationStatusEnum operationStatus;
	
	@Schema(
            description = "Module triggered the action",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private ObjectType origination;
	
	@Schema(
            description = "Module affected by the operation",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private ObjectType affected;
	
	@Schema(
            description = "Object identifier",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String objectIdentifier;
	
	@Schema(
            description = "Type of operation performed",
            requiredMode = Schema.RequiredMode.REQUIRED
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

    public OffsetDateTime getCreated() {
        return created;
    }

    public void setCreated(OffsetDateTime created) {
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
