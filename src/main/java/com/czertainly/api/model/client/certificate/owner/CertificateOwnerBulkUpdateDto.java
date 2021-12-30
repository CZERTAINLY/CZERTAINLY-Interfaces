package com.czertainly.api.model.client.certificate.owner;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CertificateOwnerBulkUpdateDto {
	
	@Schema(
            description = "Owner of the Certificates",
            required = true
    )
	private String owner;
	
	@Schema(
            description = "List of Certificate UUIDs",
            required = true
    )
	private List<String> certificateUuids;

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
}
