package com.czertainly.api.core.modal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class representing client change request
 */
public class EditClientRequestDto {
	
	@Schema(
            description = "Description of the client",
            required = false
    )
    private String description;
	
	@Schema(
            description = "Base64 content of the certificate",
            required = false
    )
    private String clientCertificate;
	
	@Schema(
            description = "UUID of the existing certificate in inventory",
            required = false
    )
    private String certificateUuid;

    public EditClientRequestDto(String clientCertificate) {
        this.clientCertificate = clientCertificate;
    }

    public EditClientRequestDto() {
    }

    public String getClientCertificate() {
        return clientCertificate;
    }

    public void setClientCertificate(String clientCertificate) {
        this.clientCertificate = clientCertificate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCertificateUuid() {
		return certificateUuid;
	}

	public void setCertificateUuid(String certificateUuid) {
		this.certificateUuid = certificateUuid;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("description", description)
                .append("clientCertificate", clientCertificate)
                .append("certificateUuid", certificateUuid)
                .toString();
    }
}

