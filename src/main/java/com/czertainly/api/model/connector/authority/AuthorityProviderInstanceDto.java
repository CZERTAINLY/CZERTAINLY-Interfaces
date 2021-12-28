package com.czertainly.api.model.connector.authority;

import com.czertainly.api.model.common.AttributeDefinition;
import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class AuthorityProviderInstanceDto extends NameAndUuidDto {

    @Schema(description = "List of Authority instance Attributes",
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
