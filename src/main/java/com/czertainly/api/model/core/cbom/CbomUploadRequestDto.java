package com.czertainly.api.model.core.cbom;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class CbomUploadRequestDto {

	@Schema(description = "Raw JSON content of Cbom document", requiredMode = Schema.RequiredMode.REQUIRED)
	private String content;

	@Schema(description = "Custom attributes")
	private List<RequestAttributeDto> customAttributes;
}
