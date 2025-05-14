package com.czertainly.api.model.core.workflows;

import com.czertainly.api.model.core.search.FilterFieldSource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExecutionItemRequestDto {
    @Schema(
            description = "Source of the field in the execution item",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private FilterFieldSource fieldSource;

    @Schema(
            description = "Field identifier of the execution item",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String fieldIdentifier;

    @Schema(
            description = "Data of the execution item"
    )
    private Serializable data;

}
