package com.czertainly.api.model.client.certificate;

import com.czertainly.api.model.core.certificate.BulkOperationStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public class BulkOperationResponse {
    @Schema(description = "Status of the operation", required = true)
    private BulkOperationStatus status;

    @Schema(description = "Number of items failed", required = true)
    private Long failedItem;

    @Schema(description = "Message for the action", required = true)
    private String message;


    public BulkOperationStatus getStatus() {
        return status;
    }

    public void setStatus(BulkOperationStatus status) {
        this.status = status;
    }

    public Long getFailedItem() {
        return failedItem;
    }

    public void setFailedItem(Long failedItem) {
        this.failedItem = failedItem;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
