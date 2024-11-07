package com.czertainly.api.model.core.auth;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.core.logging.Loggable;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDetailDto extends UserDto implements Loggable {

    @Schema(description = "User Certificate details")
    private UserCertificateDto certificate;

    @Schema(description = "Roles for the user", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<RoleDto> roles;

    @Schema(description = "List of Custom Attributes")
    private List<ResponseAttributeDto> customAttributes;

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    public UserCertificateDto getCertificate() {
        return certificate;
    }

    public void setCertificate(UserCertificateDto certificate) {
        this.certificate = certificate;
    }

    public List<ResponseAttributeDto> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<ResponseAttributeDto> customAttributes) {
        this.customAttributes = customAttributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", getUuid())
                .append("username", getUsername())
                .append("firstName", getFirstName())
                .append("lastName", getLastName())
                .append("email", getEmail())
                .append("description", getDescription())
                .append("certificate", certificate)
                .append("enabled", getEnabled())
                .append("roles", roles)
                .append("customAttributes", customAttributes)
                .toString();
    }

    @Override
    public Serializable toLogData() {
        Map<String, Object> logData = new HashMap<>();
        logData.put("uuid", getUuid());
        logData.put("username", getUsername());
        logData.put("roles", roles.stream().map(NameAndUuidDto::getName).toList());
        return (Serializable) logData;
    }
}
