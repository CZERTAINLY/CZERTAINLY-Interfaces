package com.czertainly.api.model.core.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UserRequestDto {

    @Schema(description = "Username of the user", requiredMode = Schema.RequiredMode.REQUIRED, example = "user1")
    private String username;

    @Schema(description = "First name of the user")
    private String firstName;

    @Schema(description = "Last name of the user")
    private String lastName;

    @Schema(description = "Email of the user")
    private String email;

    @Schema(description = "Description of the user")
    private String description;

    @Schema(description = "Group of the user")
    private String groupName;

    @Schema(description = "Group UUID of the user")
    private String groupUuid;

    @Schema(description = "Status of the user. True = Enabled, False = Disabled")
    private Boolean enabled;

    @Schema(description = "UUID of the Certificate")
    private String certificateUuid;

    @Schema(description = "Fingerprint of the Certificate")
    private String certificateFingerprint;

    @Schema(description = "Is System User")
    private Boolean systemUser;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
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

    public Boolean getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(Boolean systemUser) {
        this.systemUser = systemUser;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("username", username)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("email", email)
                .append("description", description)
                .append("group", groupName)
                .append("groupUuid", groupUuid)
                .append("certificateUuid", certificateUuid)
                .append("enabled", enabled)
                .append("certificateFingerprint", certificateFingerprint)
                .append("systemUser", systemUser)
                .toString();
    }
}
