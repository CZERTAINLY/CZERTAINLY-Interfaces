package com.czertainly.api.model.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BasePaginationResponseDto {
    @Schema(description = "Number of entries per page", requiredMode = Schema.RequiredMode.REQUIRED)
    private int itemsPerPage;

    @Schema(description = "Page number for the request", requiredMode = Schema.RequiredMode.REQUIRED)
    private int pageNumber;

    @Schema(description = "Number of pages available", requiredMode = Schema.RequiredMode.REQUIRED)
    private int totalPages;

    @Schema(description = "Number of items available", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long totalItems;
}
