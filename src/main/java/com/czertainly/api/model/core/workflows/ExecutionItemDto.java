package com.czertainly.api.model.core.workflows;

import com.czertainly.api.model.core.search.FilterFieldSource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExecutionItemDto {
    @Schema(
            description = "Source of the field in the execution item",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private FilterFieldSource fieldSource;

    @Schema(
            description = "Field identifier of the execution item",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String fieldIdentifier;

    @Schema(
            description = "Data of the execution item"
    )
    private Serializable data;

}
