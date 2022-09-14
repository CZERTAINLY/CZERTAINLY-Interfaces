package com.czertainly.api.model.core.auth;

import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RoleDto extends NameAndUuidDto {
    @Schema(description = "Description of the user")
    private String description;

    @Schema(description = "Is system role. True = Yes, False = No")
    private Boolean systemRole;

    public Boolean getSystemRole() {
        return systemRole;
    }

    public void setSystemRole(Boolean systemRole) {
        this.systemRole = systemRole;
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
                .append("uuid", uuid)
                .append("name", name)
                .append("description", description)
                .append("systemRole", systemRole)
                .toString();
    }
}
