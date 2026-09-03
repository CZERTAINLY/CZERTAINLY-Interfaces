package com.otilm.api.model.common;

import com.otilm.api.model.core.scheduler.PaginationRequestDto;
import com.otilm.api.model.core.search.SortDirection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springdoc.core.annotations.ParameterObject;

/**
 * Paging plus the direction of the ordering, for endpoints that sort on a single key of their own choosing. The key is
 * not carried here; each endpoint documents the one it orders by.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ParameterObject
public class SortedPaginationRequestDto extends PaginationRequestDto {

    @Schema(description = "Direction of the ordering", defaultValue = "asc")
    private SortDirection sortDirection = SortDirection.ASC;
}
