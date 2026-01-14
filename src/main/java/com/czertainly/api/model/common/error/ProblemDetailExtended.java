package com.czertainly.api.model.common.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.ProblemDetail;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProblemDetailExtended extends ProblemDetail {

    @Schema(description = "Application specific error code", requiredMode = Schema.RequiredMode.REQUIRED)
    private ErrorCode errorCode;

    @Schema(description = "Timestamp of when the error occurred", requiredMode = Schema.RequiredMode.REQUIRED)
    private OffsetDateTime timestamp;

    @Schema(description = "Correlation ID for tracking the request. Trace value from X-Request-Id or W3C Trace Context.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String correlationId;

    @Schema(description = "Indicates if the operation that caused the error can be retried safely", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private boolean retryable;

    @Schema(description = "Backoff hint, number of seconds after which the client can retry the operation. Aligns with Retry-After header.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private int retryAfterSeconds;

    @Schema(description = "List of causes that led to this error", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<ProblemDetailCause> causes;

}
