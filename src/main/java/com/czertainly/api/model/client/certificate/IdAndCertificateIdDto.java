package com.czertainly.api.model.client.certificate;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class IdAndCertificateIdDto {
	
	@Schema(
            description = "Id of the Object",
            required = true
    )
	private String uuid;
	
	@Schema(
            description = "List of Ids of the certificates in Inventory",
            required = true
    )
	private List<String> certificateIds;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<String> getCertificateIds() {
		return certificateIds;
	}

	public void setCertificateIds(List<String> certificateIds) {
		this.certificateIds = certificateIds;
	}
}
