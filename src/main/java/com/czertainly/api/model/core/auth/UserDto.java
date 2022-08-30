package com.czertainly.api.model.core.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UserDto {

    @Schema(description = "UUID of the User", required = true, example = "5b5f0784-2519-11ed-861d-0242ac120002")
    private String uuid;

    @Schema(description = "Username of the user", required = true, example = "user1")
    private String username;

    @Schema(description = "First name of the user")
    private String firstName;

    @Schema(description = "Last name of the user")
    private String lastName;

    @Schema(description = "Email of the user")
    private String email;

    @Schema(description = "Status of the user. True = Enabled, False = Disabled")
    private Boolean enabled;

    @Schema(description = "User Certificate details")
    private UserCertificateDto certificate;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public UserCertificateDto getCertificate() {
        return certificate;
    }

    public void setCertificate(UserCertificateDto certificate) {
        this.certificate = certificate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("username", username)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("email", email)
                .append("certificate", certificate)
                .append("enabled", enabled)
                .toString();
    }
}
