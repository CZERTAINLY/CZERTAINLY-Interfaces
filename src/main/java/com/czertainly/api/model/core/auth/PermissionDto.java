package com.czertainly.api.model.core.auth;

import com.czertainly.api.model.common.UuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PermissionDto extends UuidDto {

    @Schema(description = "Resource of the permission")
    private ResourceDto resource;

    @Schema(description = "Action for the permission")
    private ActionDto action;

    @Schema(description = "UUID of the Object")
    private String objectUuid;

    @Schema(description = "Is the Object Allowed. True = Allowed, False = Not Allowed")
    private Boolean isAllowed;

    public ResourceDto getResource() {
        return resource;
    }

    public void setResource(ResourceDto resource) {
        this.resource = resource;
    }

    public ActionDto getAction() {
        return action;
    }

    public void setAction(ActionDto action) {
        this.action = action;
    }

    public String getObjectUuid() {
        return objectUuid;
    }

    public void setObjectUuid(String objectUuid) {
        this.objectUuid = objectUuid;
    }

    public Boolean getAllowed() {
        return isAllowed;
    }

    public void setAllowed(Boolean allowed) {
        isAllowed = allowed;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", getUuid())
                .append("resource", resource)
                .append("action", action)
                .append("objectUuid", objectUuid)
                .append("isAllowed", isAllowed)
                .toString();
    }
}
