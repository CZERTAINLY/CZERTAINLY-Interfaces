package com.czertainly.api.core.modal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class representing administrator change request
 */
public class EditAdminRequestDto {
	
	@Schema(
            description = "Base64 Content of the certificate",
            required = false
    )
    private String adminCertificate;
	
	@Schema(
            description = "UUID of the existing certificate in inventory",
            required = true
    )
    private String certificateUuid;
	
	@Schema(
            description = "Name of the administrator",
            required = true
    )
    private String name;
	
	@Schema(
            description = "Administrator Surname",
            required = true
    )
    private String surname;
	
	@Schema(
            description = "Email Id of the admin",
            required = true
    )
    private String email;
	
	@Schema(
            description = "Role of the admin",
            required = true
    )
    private AdminRole role;
	
	@Schema(
            description = "Description for the administrator",
            required = true
    )
    private String description;

    public String getAdminCertificate() {
        return adminCertificate;
    }

    public void setAdminCertificate(String adminCertificate) {
        this.adminCertificate = adminCertificate;
    }

    public AdminRole getRole() {
        return role;
    }

    public void setRole(AdminRole role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
                .append("adminCertificate", adminCertificate)
                .append("name", name)
                .append("surname", surname)
                .append("email", email)
                .append("role", role)
                .append("description", description)
                .append("certificateUuid", certificateUuid)
                .toString();
    }
}
