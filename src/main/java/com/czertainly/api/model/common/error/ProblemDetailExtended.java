package com.czertainly.api.model.common.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.ProblemDetail;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProblemDetailExtended extends ProblemDetail {

    @Schema(description = "RFC 9457 type URI identifying the problem type",
            requiredMode = Schema.RequiredMode.REQUIRED,
            format = "uri",
            example = "https://docs.czertainly.com/problems/common/validation-failed")
    @Override
    @NonNull
    public URI getType() { return super.getType(); }

    @Schema(description = "Short human-readable summary of the problem type",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            example = "Validation failed")
    @Override
    @Nullable
    public String getTitle() { return super.getTitle(); }

    @Schema(description = "HTTP status code (MUST match the actual response code)",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "422",
            minimum = "100", maximum = "599")
    @Override
    public int getStatus() { return super.getStatus(); }

    @Schema(description = "Human-readable explanation specific to this occurrence",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            example = "One or more fields failed validation.")
    @Override
    @Nullable
    public String getDetail() { return super.getDetail(); }

    @Schema(description = "URI reference identifying the occurrence (e.g., request path or operation ID).",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            format = "uri",
            example = "/v1/certificates/123e4567-e89b-12d3-a456-426614174000")
    @Override
    @Nullable
    public URI getInstance() { return super.getInstance(); }

    @Schema(
            description = "Generic map of properties that are not known ahead of time",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Override
    @Nullable
    public Map<String, Object> getProperties() { return super.getProperties(); }

    @Schema(description = "Application specific error code", requiredMode = Schema.RequiredMode.REQUIRED)
    private ErrorCode errorCode;

    @Schema(description = "Timestamp of when the error occurred (RFC 3339 format)", requiredMode = Schema.RequiredMode.REQUIRED, examples = {"2026-01-22T15:10:20.123Z", "2026-01-22T15:10:20.123+02:00"})
    private OffsetDateTime timestamp;

    @Schema(description = "Correlation ID for tracking the request. Trace value from X-Request-Id or W3C Trace Context.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String correlationId;

    @Schema(description = "Indicates if the operation that caused the error can be retried safely", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private boolean retryable;

    @Schema(description = "Backoff hint, number of seconds after which the client can retry the operation. Aligns with Retry-After header.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private int retryAfterSeconds;

}
