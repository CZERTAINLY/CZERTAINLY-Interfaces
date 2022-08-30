package com.czertainly.api.model.core.auth;

import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ResourceDto extends NameAndUuidDto {

    @Schema(description = "Listing Endpoint")
    private String listingEndPoint;

    public String getListingEndPoint() {
        return listingEndPoint;
    }

    public void setListingEndPoint(String listingEndPoint) {
        this.listingEndPoint = listingEndPoint;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("uuid", uuid)
                .append("listingEndPoint", listingEndPoint)
                .toString();
    }
}
