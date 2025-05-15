package com.czertainly.api.model.common.events.data;

import com.czertainly.api.model.client.approval.ApprovalStatusEnum;
import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApprovalEventData implements EventData {
    @Schema(description = "Approval UUID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String approvalUuid;

    @Schema(description = "Approval profile UUID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String approvalProfileUuid;

    @Schema(description = "Approval profile name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String approvalProfileName;

    @Schema(description = "Version of the Approval profile", requiredMode = Schema.RequiredMode.REQUIRED)
    private int version;

    @Schema(description = "Status of the Approval", requiredMode = Schema.RequiredMode.REQUIRED)
    private ApprovalStatusEnum status;

    @Schema(description = "Approval expiry date", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Date expiryAt;

    @Schema(description = "Date of resolution of the Approval", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Date closedAt;

    @Schema(description = "Approval associated resource", requiredMode = Schema.RequiredMode.REQUIRED)
    private Resource resource;

    @Schema(description = "Approval associated resource action", requiredMode = Schema.RequiredMode.REQUIRED)
    private String resourceAction;

    @Schema(description = "Approval associated object UUID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String objectUuid;

    @Schema(description = "UUID of the user that created approval", requiredMode = Schema.RequiredMode.REQUIRED)
    private String creatorUuid;

    @Schema(description = "Username of the user that created approval", requiredMode = Schema.RequiredMode.REQUIRED)
    private String creatorUsername;

}
