package com.czertainly.api.model.core.client;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.core.certificate.CertificateDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class representing client
 */
public class ClientDto extends NameAndUuidDto {
	
	@Schema(
            description = "Client serial Number",
            required = true
    )
    private String serialNumber;
	
	@Schema(
            description = "Client Certificate Information",
            required = true
    )
    private CertificateDto certificate;
	
	@Schema(
            description = "Client description",
            required = true
    )
    private String description;
	
	@Schema(
            description = "Status of the client / Is Client enabled?",
            required = true
    )
    private Boolean enabled;

    @Override
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

	public CertificateDto getCertificate() {
		return certificate;
	}

	public void setCertificate(CertificateDto certificate) {
		this.certificate = certificate;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("description", description)
                .append("enabled", enabled)
                .append("certificate", certificate)
                .append("serialNumber", serialNumber)
                .toString();
    }
}

