package com.czertainly.api.model.connector.discovery;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DiscoveryDataRequestDto {

    @Schema(description = "Name of the Discovery",
            required = true)
    private String name;

    @Schema(description = "Discovery Kind",
            required = true)
    private String kind;

    @Schema(description = "Starting index of the Certificate",
            required = true)
    private Integer startIndex;

    @Schema(description = "Last index of the Certificate",
            required = true)
    private Integer endIndex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("kind", kind)
                .append("startIndex", startIndex)
                .append("endIndex", endIndex)
                .toString();
    }
}
