package com.czertainly.api.model.core.auth;

import com.czertainly.api.model.common.Named;
import com.czertainly.api.model.core.admin.AdminRole;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AuthProfileDto implements Named {
	
	@Schema(
            description = "Username",
            required = true
    )
    private String username;
	
	@Schema(
            description = "Name of the user",
            required = true
    )
    private String name;
	
	@Schema(
            description = "Surname of the user",
            required = true
    )
    private String surname;
	
	@Schema(
            description = "Role of User",
            required = true
    )
    private AdminRole role;
	
	@Schema(
            description = "Email address of the user",
            required = true
    )
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public AdminRole getRole() {
        return role;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("username", username)
                .append("name", name)
                .append("surname", surname)
                .append("role", role)
                .append("email", email)
                .toString();
    }
}
