package com.czertainly.api.model.common.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.net.URI;

@Getter
@Schema(enumAsRef = true)
public enum ErrorCode {
    VALIDATION_FAILED(ProblemTypeCategory.COMMON, "Validation failed", HttpStatus.UNPROCESSABLE_ENTITY, true);

    private final ProblemTypeCategory category;
    private final String title;
    private final HttpStatus status;
    private final boolean retryable;

    ErrorCode(ProblemTypeCategory category, String title, HttpStatus status, boolean retryable) {
        this.category = category;
        this.title = title;
        this.status = status;
        this.retryable = retryable;
    }

    public URI getType() {
        return URI.create("https://docs.czertainly.com/problems/%s/%s".formatted(this.category.getCode(), this.name()));
    }

}
