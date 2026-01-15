package com.czertainly.api.model.core.cbom;

import io.swagger.v3.oas.annotations.media.Schema;

public class CbomDetailDto extends CbomListDto {

	@Schema(description = "Raw JSON content of Cbom document", requiredMode = Schema.RequiredMode.REQUIRED)
	private String content;

	// Getter and setter

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
