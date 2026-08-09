package com.otilm.api.model.core.logging.records;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Builder;

@Builder
public record SourceRecord(@NotNull String method, @NotNull String path, String contentType, String ipAddress,
        String userAgent) implements Serializable {
}
