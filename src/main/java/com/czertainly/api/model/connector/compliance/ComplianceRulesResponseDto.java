package com.czertainly.api.model.connector.compliance;

import com.czertainly.api.model.common.AttributeDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/*
List of rules information from the Compliance Provider. These rules will
have name, uuid and the attributes. The attributes of the rules is used
to request for additional information for the rule.
 */
public class ComplianceRulesResponseDto {
    @Schema(description = "UUID of the rule", required = true, example = "166b5cf52-63f2-11ec-90d6-0242ac120003")
    private String uuid;

    @Schema(description = "Name of the rule", required = true, example = "Rule1")
    private String name;

    @Schema(description = "Rule attributes")
    private List<AttributeDefinition> attributes;

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
