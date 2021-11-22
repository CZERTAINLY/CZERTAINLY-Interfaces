package com.czertainly.api.model.raprofile;

import com.czertainly.api.model.AttributeDefinition;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class EditRaProfileRequestDto {
    private String description;
    private String caInstanceUuid;
    private List<AttributeDefinition> attributes;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCaInstanceUuid() {
        return caInstanceUuid;
    }

    public void setCaInstanceUuid(String caInstanceUuid) {
        this.caInstanceUuid = caInstanceUuid;
    }

    public List<AttributeDefinition> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDefinition> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("description", description)
                .append("caInstanceId", caInstanceUuid)
                .append("attributes", attributes)
                .toString();
    }
}
