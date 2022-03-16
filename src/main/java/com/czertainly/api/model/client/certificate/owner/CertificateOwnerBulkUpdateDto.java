package com.czertainly.api.model.client.certificate.owner;

import com.czertainly.api.model.client.certificate.CertificateFilterRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CertificateOwnerBulkUpdateDto {
	
	@Schema(
            description = "Owner of the Certificates",
            required = true
    )
	private String owner;
	
	@Schema(
            description = "List of Certificate UUIDs"
    )
	private List<String> certificateUuids;

	@Schema(
			description = "Certificate filter input"
	)
	private List<CertificateFilterRequestDto> filters;

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
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
}
