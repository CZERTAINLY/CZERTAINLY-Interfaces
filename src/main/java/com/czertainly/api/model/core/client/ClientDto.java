package com.czertainly.api.model.core.client;

import com.czertainly.api.model.core.certificate.CertificateDto;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.czertainly.api.model.commons.Identified;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class representing client
 */
public class ClientDto implements Identified {
	
	@Schema(
            description = "Id of the client",
            required = true
    )
    private Long id;
	
	@Schema(
            description = "UUID of the client",
            required = true
    )
    private String uuid;
	
	@Schema(
            description = "Client serial Number",
            required = true
    )
    private String serialNumber;
	
	@Schema(
            description = "Name of the client",
            required = true
    )
    private String name;
	
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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
                .append("id", id)
                .append("uuid", uuid)
                .append("name", name)
                .append("description", description)
                .append("enabled", enabled)
                .append("certificate", certificate)
                .append("serialNumber", serialNumber)
                .toString();
    }
}

