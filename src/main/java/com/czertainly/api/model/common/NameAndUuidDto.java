package com.czertainly.api.model.common;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class NameAndUuidDto implements Serializable {

    @Schema(description = "Object identifier",
            required = true)
    protected String uuid;
    @Schema(description = "Object Name",
            required = true)
    protected String name;

    public NameAndUuidDto() {
        super();
    }

    public NameAndUuidDto(String uuid, String name) {
        super();
        this.uuid = uuid;
        this.name = name;
    }

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
                .append("uuid", uuid)
                .append("name", name)
                .toString();
    }
}
