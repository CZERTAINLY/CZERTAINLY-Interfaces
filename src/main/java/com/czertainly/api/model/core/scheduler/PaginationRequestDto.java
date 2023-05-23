package com.czertainly.api.model.core.scheduler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PaginationRequestDto {

    @Schema(description = "Number of entries per page", defaultValue = "10", maximum = "1000")
    private Integer itemsPerPage;

    @Schema(description = "Page number for the request", defaultValue = "1")
    private Integer pageNumber;

}
