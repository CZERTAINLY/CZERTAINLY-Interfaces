package com.czertainly.api.model.core.logging.records;

import com.czertainly.api.model.core.logging.enums.OperationResult;
import com.czertainly.api.model.core.logging.enums.Operation;
import com.czertainly.api.model.core.logging.enums.Module;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.util.Map;

@Builder
public record LogRecord (
        @NotNull String version,
        @NotNull boolean audited,
        @NotNull Module module,
        @NotNull ActorRecord actor,
        SourceRecord source,
        @NotNull ResourceRecord resource,
        ResourceRecord affiliatedResource,
        @NotNull Operation operation,
        @NotNull OperationResult operationResult,
        String message,
        Serializable operationData,
        Map<String, Object> additionalData
) implements Serializable {}