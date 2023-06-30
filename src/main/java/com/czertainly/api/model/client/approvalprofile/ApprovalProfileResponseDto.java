package com.czertainly.api.model.client.approvalprofile;

import com.czertainly.api.model.core.scheduler.PaginationResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class ApprovalProfileResponseDto extends PaginationResponseDto {

    private List<ApprovalProfileDto> approvalProfiles;

}
