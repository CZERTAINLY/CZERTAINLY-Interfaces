package com.czertainly.api.model.common.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProblemDetailCause {

    @Schema(description = "Type of the error", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Detailed reason for the error", requiredMode = Schema.RequiredMode.REQUIRED)
    private String reason;

    @Schema(description = "Rule violated causing the error", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String rule;

}
