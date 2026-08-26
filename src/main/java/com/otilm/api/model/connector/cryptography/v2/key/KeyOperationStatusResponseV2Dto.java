package com.otilm.api.model.connector.cryptography.v2.key;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.connector.common.v2.OperationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** Common state of an asynchronous key-operation status response. */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class KeyOperationStatusResponseV2Dto {

    @Schema(description = "Operation status as known to the connector", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "status is required")
    private OperationStatus status;

    @Schema(description = "Failure or cancellation detail when status is `failed` or `cancelled`",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String reason;

    @JsonIgnore
    @Schema(hidden = true)
    @AssertTrue(message = "reason is required when status is failed or cancelled and must be absent otherwise")
    public final boolean isReasonConsistentWithStatus() {
        if (status == null) {
            return true;
        }

        return switch (status) {
            case FAILED, CANCELLED -> reason != null && !reason.isBlank();
            case IN_PROGRESS, COMPLETED -> reason == null;
        };
    }

    protected final boolean isResultConsistentWithStatus(Object result) {
        if (status == null) {
            return true;
        }
        return status == OperationStatus.COMPLETED ? result != null : result == null;
    }

    @JsonAnySetter
    @Schema(hidden = true)
    public final void rejectUnknownProperty(String property, Object ignoredValue) {
        throw new IllegalArgumentException("Unsupported key-operation status response property: " + property);
    }
}
