package com.czertainly.api.model.core.cbom;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CbomUploadRequestDto {

	@Schema(description = "Raw JSON content of CBOM document", requiredMode = Schema.RequiredMode.REQUIRED)
	private Map<String, Object> content;

	@Schema(description = "Custom attributes")
	private List<RequestAttributeDto> customAttributes;
}
