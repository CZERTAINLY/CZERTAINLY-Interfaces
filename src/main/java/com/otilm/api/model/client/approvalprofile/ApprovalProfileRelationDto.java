package com.otilm.api.model.client.approvalprofile;

import com.otilm.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Data;

@Data
public class ApprovalProfileRelationDto {
    @Schema(description = "UUID of the Approval profile relation", requiredMode = Schema.RequiredMode.REQUIRED)
    private String uuid;

    @Schema(description = "UUID of the Approval profile", requiredMode = Schema.RequiredMode.REQUIRED)
    private String approvalProfileUuid;

    @Schema(description = "Resource associated with approval profile", requiredMode = Schema.RequiredMode.REQUIRED)
    private Resource resource;

    @Schema(description = "Resource object UUID associated with approval profile",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID resourceUuid;

    @Schema(description = "Resource action associated with approval profile",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String action;

}
