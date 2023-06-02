package com.czertainly.api.model.core.audit;

import com.czertainly.api.model.core.scheduler.PaginationResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class AuditLogResponseDto extends PaginationResponseDto {

    @Schema(
            description = "Audit log items",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<AuditLogDto> items;
}
