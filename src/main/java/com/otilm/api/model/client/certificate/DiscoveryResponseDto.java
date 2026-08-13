package com.otilm.api.model.client.certificate;

import com.otilm.api.model.client.discovery.DiscoveryListDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class DiscoveryResponseDto {
    @Schema(description = "Discoveries", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<DiscoveryListDto> discoveries;

    @Schema(description = "Number of entries per page", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer itemsPerPage;

    @Schema(description = "Page number for the request", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer pageNumber;

    @Schema(description = "Number of pages available", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer totalPages;

    @Schema(description = "Number of items available", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long totalItems;
}
