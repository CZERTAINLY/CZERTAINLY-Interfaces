package com.czertainly.api.model.client.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UpdateUserRequestDto {

    @Schema(description = "Description of the user")
    private String description;

    @Schema(description = "First name of the user")
    private String firstName;

    @Schema(description = "Last name of the user")
    private String lastName;

    @Schema(description = "Email of the user", required = true)
    private String email;

    @Schema(
            description = "Base64 Content of the admin certificate",
            required = false
    )
    private String certificateData;

    @Schema(
            description = "UUID of the existing certificate in the Inventory. Mandatory if certificate is not provided",
            required = false
    )
    private String certificateUuid;

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

    public String getCertificateUuid() {
        return certificateUuid;
    }

    public void setCertificateUuid(String certificateUuid) {
        this.certificateUuid = certificateUuid;
    }

    public String getCertificateData() {
        return certificateData;
    }

    public void setCertificateData(String certificateData) {
        this.certificateData = certificateData;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("firstName", firstName)
                .append("description", description)
                .append("lastName", lastName)
                .append("email", email)
                .append("certificateUuid", certificateUuid)
                .append("certificateData", certificateData)
                .toString();
    }
}
