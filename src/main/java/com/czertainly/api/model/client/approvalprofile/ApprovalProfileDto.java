package com.czertainly.api.model.client.approvalprofile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ApprovalProfileDto {

    @Schema(
            description = "UUID of the Approval profile",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String uuid;

    @Schema(description = "Name of the Approval profile",
            example = "ApprovalProfile1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Version of the Approval profile",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private int version;

    @Schema(description = "Description of the Approval profile",
            example = "Detail description of the approval profile",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @Schema(description = "Enable of the Approval profile",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean enabled;

    @Schema(description = "Expiration of the Approval profile in hours",
            example = "30",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private int expiry;

    @Schema(description = "Number of the Approval profile steps",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private int numberOfSteps;

}
