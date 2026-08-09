package com.otilm.api.model.client.approval;

import com.otilm.api.model.core.scheduler.PaginationResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApprovalResponseDto extends PaginationResponseDto {

    @Schema(description = "List of the Approvals", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ApprovalDto> approvals;

}
