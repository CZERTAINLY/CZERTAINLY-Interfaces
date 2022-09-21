package com.czertainly.api.model.core.auth;

import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ResourceDto extends NameAndUuidDto {

    @Schema(description = "Resource label", required = true)
    private String displayName;

    @Schema(description = "Listing Endpoint")
    private String listingEndPoint;

    @Schema(description = "If resource has Object access permissions. True = Yes, False = No", required = true)
    private Boolean objectAccess;

    public String getListingEndPoint() {
        return listingEndPoint;
    }

    public void setListingEndPoint(String listingEndPoint) {
        this.listingEndPoint = listingEndPoint;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getObjectAccess() {
        return objectAccess;
    }

    public void setObjectAccess(Boolean objectAccess) {
        this.objectAccess = objectAccess;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("uuid", uuid)
                .append("listingEndPoint", listingEndPoint)
                .append("displayName", displayName)
                .append("objectAccess", objectAccess)
                .toString();
    }
}
