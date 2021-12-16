package com.czertainly.api.model.connector;

import com.czertainly.api.model.Identified;
import com.czertainly.api.model.NameIdUuidDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FunctionGroupDto extends BaseFunctionGroupDto implements Identified {

    @JsonIgnore
    @Schema(description = "Id of the Function Group",
            example = "1",
            required = true)
    private Long id;
    @Schema(description = "UUID of the function group",
            example = "204a57f6-2ed3-45b6-bf09-af8b8c900e33",
            required = true)
    private String uuid;
    @Schema(description = "Function Group Name",
            required = true)
    private String name;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("functionGroupCode", functionGroupCode)
                .append("kinds", kinds)
                .append("endPoints", endPoints)
                .append("id", id)
                .append("uuid", uuid)
                .append("name", name)
                .toString();
    }
}
