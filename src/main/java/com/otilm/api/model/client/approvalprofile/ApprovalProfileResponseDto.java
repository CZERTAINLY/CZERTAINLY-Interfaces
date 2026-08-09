package com.otilm.api.model.client.approvalprofile;

import com.otilm.api.model.core.scheduler.PaginationResponseDto;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApprovalProfileResponseDto extends PaginationResponseDto {

    private List<ApprovalProfileDto> approvalProfiles;

}
