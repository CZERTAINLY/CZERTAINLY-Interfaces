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

	@Schema(
			description = "Match all based on filter flag. true = yes, false = no. Mandatory if filters field should be used"
	)
	private Boolean allSelect;

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

	public Boolean isAllSelect() {
		return allSelect;
	}

	public void setAllSelect(Boolean allSelect) {
		this.allSelect = allSelect;
	}
}
