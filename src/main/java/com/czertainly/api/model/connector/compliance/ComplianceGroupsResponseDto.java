package com.czertainly.api.model.connector.compliance;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/*
List of groups information from the Compliance Provider. These groups will
have name, uuid and the attributes.
 */
public class ComplianceGroupsResponseDto {
    @Schema(description = "UUID of the group", required = true, example = "166b5cf52-63f2-11ec-90d6-0242ac120003")
    private String uuid;

    @Schema(description = "Name of the group", required = true, example = "RFC")
    private String name;

    @Schema(description = "Description of the group", required = false, example = "Sample description of the group")
    private String description;

    //Default getters and setters

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
                .append("uuid", uuid)
                .append("name", name)
                .append("description", description)
                .toString();
    }
}
