package com.czertainly.api.model.core.cbom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.czertainly.api.model.common.BasePaginationResponseDto;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class CbomListResponseDto extends BasePaginationResponseDto {

	private List<CbomItemDto> items;
}

