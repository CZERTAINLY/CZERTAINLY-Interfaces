package com.czertainly.api.model.connector.compliance;

import com.czertainly.api.model.common.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/*
Contains the description for rules and its validation results. This object is part of
the ComplianceResponseDto and describes in detail the list of rules applied and their
individual status.
 */
public class ComplianceRequestRulesDto {
    @Schema(description = "UUID of the rule",
            required = true,
            example = "166b5cf52-63f2-11ec-90d6-0242ac120003")
    private String uuid;

    @Schema(description = "Attributes for the rule")
    private List<RequestAttributeDto> attributes;

    //Default getters and setters

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<RequestAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<RequestAttributeDto> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("attributes", attributes)
                .toString();
    }
}
