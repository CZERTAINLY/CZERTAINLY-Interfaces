package com.czertainly.api.model.connector.authority;

import com.czertainly.api.model.commons.AttributeDefinition;
import com.czertainly.api.model.commons.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class ConnectorAuthorityInstanceRequestDto {

    @Schema(description = "Authority instance name",
            required = true)
    private String name;

    @Schema(description = "Kind of Authority instance",
            required = true)
    private String kind;

    @Schema(description = "List of Authority instance Attributes",
            implementation = List.class,
            required = true)
    private List<AttributeDefinition> attributes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
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
                .append("kind", kind)
                .append("name", name)
                .append("attributes", attributes)
                .toString();
    }
}
