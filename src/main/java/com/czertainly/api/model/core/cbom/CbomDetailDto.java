package com.czertainly.api.model.core.cbom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

@Data
public class CbomDetailDto extends CbomItemDto {

	@Schema(description = "Raw JSON content of CBOM document", requiredMode = Schema.RequiredMode.REQUIRED)
	private Map<String, Object> content;
}
