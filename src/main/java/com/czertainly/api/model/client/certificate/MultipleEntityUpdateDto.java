package com.czertainly.api.model.client.certificate;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class MultipleEntityUpdateDto {
	
	@Schema(
            description = "UUID of Entity",
            required = true
    )
	private String uuid;
	
	@Schema(
            description = "List of UUIDs of the Certificates"
    )
	private List<String> certificateUuids;

	@Schema(
			description = "Certificate filter input"
	)
	private List<CertificateFilterRequestDto> filters;

	@Schema(
			description = "Match all based on filter flag. true = yes, false = no. Mandatory if filters field should be used"
	)
	private Boolean allSelect;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<String> getCertificateUuids() {
		return certificateUuids;
	}

	public void setCertificateUuids(List<String> certificateUuids) {
		this.certificateUuids = certificateUuids;
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
