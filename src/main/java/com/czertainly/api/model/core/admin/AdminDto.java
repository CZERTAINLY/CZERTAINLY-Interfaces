package com.czertainly.api.model.core.admin;

import com.czertainly.api.model.core.certificate.CertificateDto;
import com.czertainly.api.model.commons.Identified;
import io.swagger.v3.oas.annotations.media.Schema;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class representing administrator
 */
public class AdminDto implements Identified {
	
	@Schema(
            description = "Id of the Administrator",
            required = true
    )
    private Long id;
	
	@Schema(
            description = "UUID of the Administrator",
            required = true
    )
    private String uuid;
	
	@Schema(
            description = "Administrator Username",
            required = true
    )
    private String username;
	
	@Schema(
            description = "Administrator Certificate Details",
            required = true
    )
    private CertificateDto certificate;
	
	@Schema(
            description = "Role of the administrator",
            required = true
    )
    private AdminRole role;
	
	@Schema(
            description = "Administrator Email",
            required = true
    )
    private String email;
	
	@Schema(
            description = "Serial Number of the administrator",
            required = true
    )
    private String serialNumber;
	
	@Schema(
            description = "Username of the administrator",
            required = true
    )
    private String name;
	
	@Schema(
            description = "Administrator description",
            required = true
    )
    private String description;
	
	@Schema(
            description = "Is the admin enabled?",
            required = true
    )
    private Boolean enabled;
	
	@Schema(
            description = "Surname of administrator",
            required = true
    )
    private String surname;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(AdminRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

	public AdminRole getRole() {
		return role;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("uuid", uuid)
                .append("username", username)
                .append("role", role)
                .append("certificate",certificate)
                .toString();
    }


}

