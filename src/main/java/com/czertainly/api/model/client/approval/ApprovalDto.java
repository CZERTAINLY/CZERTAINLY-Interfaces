package com.czertainly.api.model.client.approval;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ApprovalDto {

    @Schema(
            description = "UUID of the Approval",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String approvalUuid;

    @Schema(
            description = "Version of the Approval profile",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int version;

    @Schema(
            description = "Creation date of the Approval",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Date createdAt;

    @Schema(
            description = "Date of resolution of the Approval",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Date closedAt;

    @Schema(
            description = "Status of the Approval",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String status;

    @Schema(
            description = "Resource of the Approval",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String resource;

    @Schema(
            description = "Resource action of the Approval",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String resourceAction;

    @Schema(
            description = "UUID of the target object of the Approval",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String objectUuid;

    @Schema(
            description = "Name of the Approval profile",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String approvalProfileName;

    @Schema(
            description = "UUID of the Approval profile",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String approvalProfileUuid;

    public ApprovalDto(final ApprovalDto approvalDto) {
        this.approvalProfileName = approvalDto.getApprovalProfileName();
        this.approvalProfileUuid = approvalDto.getApprovalProfileUuid();
        this.approvalUuid = approvalDto.getApprovalUuid();
        this.closedAt = approvalDto.getClosedAt();
        this.createdAt = approvalDto.getCreatedAt();
        this.objectUuid = approvalDto.getObjectUuid();
        this.status = approvalDto.getStatus();
        this.version = approvalDto.getVersion();
        this.resource = approvalDto.getResource();
        this.resourceAction = approvalDto.getResourceAction();
    }
}
