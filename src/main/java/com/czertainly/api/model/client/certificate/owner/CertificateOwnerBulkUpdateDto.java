package com.czertainly.api.model.client.certificate.owner;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class CertificateOwnerBulkUpdateDto {
	
	@Schema(
            description = "Owner of the Certificates",
            required = true
    )
	private String owner;
	
	@Schema(
            description = "List of certifiate Ids",
            required = true
    )
	private List<String> certificateIds;

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public List<String> getCertificateIds() {
		return certificateIds;
	}

	public void setCertificateIds(List<String> certificateIds) {
		this.certificateIds = certificateIds;
	}
}
