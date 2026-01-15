package com.czertainly.api.model.core.cbom;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CbomUploadRequestDto {

	@Schema(description = "Raw JSON content of Cbom document", requiredMode = Schema.RequiredMode.REQUIRED)
	private String content;

	@Schema(description = "Custom attributes")
	private List<RequestAttributeDto> customAttributes;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<RequestAttributeDto> getCustomAttributes() {
		return customAttributes;
	}

	public void setCustomAttributes(List<RequestAttributeDto> customAttributes) {
		this.customAttributes = customAttributes;
	}
}
