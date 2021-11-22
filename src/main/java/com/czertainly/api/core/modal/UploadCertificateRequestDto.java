package com.czertainly.api.core.modal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class representing new certificate upload request
 */
public class UploadCertificateRequestDto {
	
	@Schema(
            description = "Base64 Content of the certificate",
            required = true
    )
	private String certificate;

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("certificate", certificate)
				.toString();
	}
}
