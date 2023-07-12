package com.czertainly.api.model.client.approval;

import com.czertainly.api.model.client.approvalprofile.ApprovalStepDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ApprovalApprovalStepDto extends ApprovalStepDto {

    @Schema(
            description = "UUID of the Approval step",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String approvalStepUuid;

    @Schema(
            description = "List of the approval recipient related for this step",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<ApprovalStepRecipientDto> approvalStepRecipients;

    public ApprovalApprovalStepDto(final ApprovalStepDto approvalStepDto) {
        super(approvalStepDto.getUserUuid(), approvalStepDto.getRoleUuid(), approvalStepDto.getGroupUuid(), approvalStepDto.getDescription(), approvalStepDto.getOrder(), approvalStepDto.getRequiredApprovals());
    }
}
