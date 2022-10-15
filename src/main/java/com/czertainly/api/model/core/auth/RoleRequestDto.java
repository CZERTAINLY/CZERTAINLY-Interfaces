package com.czertainly.api.model.core.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RoleRequestDto {

    @Schema(description = "Name of the role")
    private String name;

    @Schema(description = "Description for the role")
    private String description;

    @Schema(description = "Is System User")
    private Boolean systemRole;

    @Schema(description = "Permissions for the role")
    private RolePermissionsRequestDto permissions;

    public Boolean getSystemRole() {
        return systemRole;
    }

    public void setSystemRole(Boolean systemRole) {
        this.systemRole = systemRole;
    }

    public RolePermissionsRequestDto getPermissions() {
        return permissions;
    }

    public void setPermissions(RolePermissionsRequestDto permissions) {
        this.permissions = permissions;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("description", description)
                .append("permissions", permissions)
                .append("systemRole", systemRole)
                .toString();
    }
}
