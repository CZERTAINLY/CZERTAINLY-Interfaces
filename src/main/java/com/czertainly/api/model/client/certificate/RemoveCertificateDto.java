package com.czertainly.api.model.client.certificate;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class RemoveCertificateDto {
	
	@Schema(
            description = "UUIDs of the certificate",
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
