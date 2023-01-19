package com.czertainly.api.model.client.certificate;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class representing new certificate upload request
 */
public class UploadCertificateRequestDto {
	
	@Schema(
            description = "Base64 Content of the Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
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
