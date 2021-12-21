package com.czertainly.api.model.ca;

import com.czertainly.api.model.AttributeDefinition;
import com.czertainly.api.model.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class ConnectorCAInstanceDto extends NameAndUuidDto {

    @Schema(description = "List of Authority instance Attributes",
            implementation = List.class,
            required = true)
    private List<AttributeDefinition> attributes;


    public List<AttributeDefinition> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDefinition> attributes) {
        this.attributes = attributes;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("attributes", attributes)
                .toString();
    }
}
