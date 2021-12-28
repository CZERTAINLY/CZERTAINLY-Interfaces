package com.czertainly.api.model.connector.discovery;

import com.czertainly.api.model.commons.AttributeDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class GetDiscoveryProviderRequestDto {

    @Schema(description = "Name of the discovery",
            required = true)
    private String name;

    @Schema(description = "Page number of the Discovery",
            required = true)
    private Integer pageNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("name", name)
                .toString();
    }
}
