package com.czertainly.api.model.client.certificate;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class RemoveCertificateDto {
	
	@Schema(
            description = "UUIDs of the Certificate",
            required = true
    )
	private List<String> uuids;

	public List<String> getUuids() {
		return uuids;
	}

	public void setUuids(List<String> uuids) {
		this.uuids = uuids;
	}
	
}
