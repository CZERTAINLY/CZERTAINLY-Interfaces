package com.czertainly.api.model.client.admin;

import com.czertainly.api.model.core.admin.AdminRole;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class representing administrator authorization request
 */
public class AddAdminRequestDto {
	@Schema(
            description = "Base64 Content of the admin certificate",
            required = false
    )
    private String adminCertificate;
	
	@Schema(
            description = "UUID of the existing certificate in the Inventory. Mandatory if certificate is not provided",
            required = false
    )
    private String certificateUuid;
	
	@Schema(
            description = "Username of the administrator",
            required = true
    )
    private String username;
	
	@Schema(
            description = "Name of the administrator",
            required = true
    )
    private String name;
	
	@Schema(
            description = "Surname of the administrator",
            required = true
    )
    private String surname;
	
	@Schema(
            description = "Email of the administrator",
            required = true
    )
    private String email;
	
	@Schema(
            description = "Role of the administrator",
            required = true
    )
    private AdminRole role;
	
	@Schema(
            description = "Enabled flag. true = enabled, false = disabled",
            required = true
    )
    private Boolean enabled;
	
	@Schema(
            description = "Description for the administrator"
    )
    private String description;


    public String getAdminCertificate() {
        return adminCertificate;
    }

    public void setAdminCertificate(String adminCertificate) {
        this.adminCertificate = adminCertificate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AdminRole getRole() {
        return role;
    }

    public void setRole(AdminRole role) {
        this.role = role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
                .append("username", username)
                .append("name", name)
                .append("surname", surname)
                .append("email", email)
                .append("role", role)
                .append("enabled", enabled)
                .append("description", description)
                .append("certificateUuid", certificateUuid)
                .toString();
    }
}

