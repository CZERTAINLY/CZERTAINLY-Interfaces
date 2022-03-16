package com.czertainly.api.model.client.certificate;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class RemoveCertificateDto {
	
	@Schema(
            description = "UUIDs of the Certificate"
    )
	private List<String> uuids;

	@Schema(
			description = "Certificate filter input"
	)
	private List<CertificateFilterRequestDto> filters;

	public List<String> getUuids() {
		return uuids;
	}

	public void setUuids(List<String> uuids) {
		this.uuids = uuids;
	}

	public List<CertificateFilterRequestDto> getFilters() {
		return filters;
	}

	public void setFilters(List<CertificateFilterRequestDto> filters) {
		this.filters = filters;
	}
}
