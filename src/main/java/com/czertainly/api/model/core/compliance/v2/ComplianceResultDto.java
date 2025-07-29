package com.czertainly.api.model.core.compliance.v2;

import com.czertainly.api.model.core.compliance.ComplianceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class ComplianceResultDto {

    @Schema(description = "Overall compliance result status", requiredMode = Schema.RequiredMode.REQUIRED)
    private ComplianceStatus status;

    @Schema(description = "Date of the most recent compliance check", requiredMode = Schema.RequiredMode.REQUIRED)
    private OffsetDateTime timestamp;

    @Schema(description = "Not Compliant Rules", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<UUID> notCompliant = new ArrayList<>();

    @Schema(description = "Not Applicable Rules", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<UUID> notApplicable = new ArrayList<>();

    @Schema(description = "Not Available Rules", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<UUID> notAvailable = new ArrayList<>();

}
