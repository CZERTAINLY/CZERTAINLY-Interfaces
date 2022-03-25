package com.czertainly.api.model.client.certificate;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class RemoveCertificateDto {
	
	@Schema(
			description = "List of Certificate UUIDs"
    )
	private List<String> uuids;

	@Schema(
			description = "Certificate filter input"
	)
	private List<SearchFilterRequestDto> filters;

	public List<String> getUuids() {
		return uuids;
	}

	public void setUuids(List<String> uuids) {
		this.uuids = uuids;
	}

	public List<SearchFilterRequestDto> getFilters() {
		return filters;
	}

	public void setFilters(List<SearchFilterRequestDto> filters) {
		this.filters = filters;
	}
}
