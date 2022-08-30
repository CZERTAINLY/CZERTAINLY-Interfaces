package com.czertainly.api.model.core.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UserRequestDto {

    @Schema(description = "Username of the user", required = true, example = "user1")
    private String username;

    @Schema(description = "First name of the user")
    private String firstName;

    @Schema(description = "Last name of the user")
    private String lastName;

    @Schema(description = "Email of the user", required = true)
    private String email;

    @Schema(description = "Status of the user. True = Enabled, False = Disabled")
    private Boolean enabled;

    @Schema(description = "UUID of the Certificate")
    private String certificateUuid;

    @Schema(description = "Fingerprint of the Certificate")
    private String certificateFingerprint;

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

    public String getCertificateUuid() {
        return certificateUuid;
    }

    public void setCertificateUuid(String certificateUuid) {
        this.certificateUuid = certificateUuid;
    }

    public String getCertificateFingerprint() {
        return certificateFingerprint;
    }

    public void setCertificateFingerprint(String certificateFingerprint) {
        this.certificateFingerprint = certificateFingerprint;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("username", username)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("email", email)
                .append("certificateUuid", certificateUuid)
                .append("enabled", enabled)
                .append("certificateFingerprint", certificateFingerprint)
                .toString();
    }
}
