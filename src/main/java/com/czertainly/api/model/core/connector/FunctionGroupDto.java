package com.czertainly.api.model.core.connector;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FunctionGroupDto extends BaseFunctionGroupDto {

    @Schema(description = "UUID of the Function Group",
            examples = {"204a57f6-2ed3-45b6-bf09-af8b8c900e33"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String uuid;
    @Schema(description = "Function Group Name",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

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
                .append("uuid", uuid)
                .append("name", name)
                .toString();
    }
}
