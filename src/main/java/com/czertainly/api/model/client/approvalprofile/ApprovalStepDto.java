package com.czertainly.api.model.client.approvalprofile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
public class ApprovalStepDto {

    @Schema(description = "Reference to responsible user to Approved the action",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID userUuid;

    @Schema(description = "Reference to responsible role of the users to Approved the action",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID roleUuid;

    @Schema(description = "Reference to responsible group of the users to Approved the action",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID groupUuid;

    @Schema(description = "Description of the Approval step",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @Schema(description = "Order of the position in the approval steps flow",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private int order;

    @Schema(description = "Count of the required approvals for the Approval step, by default there is 1 approval needed.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private int requiredApprovals;


}
