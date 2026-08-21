package com.otilm.api.model.client.certificate;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.Data;

@Data
public class SearchRequestDto {

    @Schema(description = "Certificate filter input")
    private List<SearchFilterRequestDto> filters;

    @Schema(description = "Number of entries per page", defaultValue = "10", maximum = "1000")
    private Integer itemsPerPage = 10;

    @Schema(description = "Page number for the request", defaultValue = "1")
    private Integer pageNumber = 1;

    @Valid
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Ordering of the whole result set. When omitted, the endpoint's own default ordering "
            + "applies, unchanged from before this field existed.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private SearchSortRequestDto sort;

    @Valid
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Fields to return as columns. When omitted, the response carries the endpoint's full "
            + "default shape, unchanged from before this field existed. Requesting attribute-sourced fields adds "
            + "their values to each returned object.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<SearchColumnRequestDto> columns;
}
