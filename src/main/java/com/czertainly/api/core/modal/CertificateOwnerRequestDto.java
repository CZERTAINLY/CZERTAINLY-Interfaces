package com.czertainly.api.core.modal;

import io.swagger.v3.oas.annotations.media.Schema;

public class CertificateOwnerRequestDto {
	
	@Schema(
            description = "Owner of the certificate",
            required = true
    )
	private String owner;

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	
}
