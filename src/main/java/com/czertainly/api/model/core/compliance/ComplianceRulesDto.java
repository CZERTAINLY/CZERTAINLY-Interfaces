package com.czertainly.api.model.core.compliance;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.ResponseAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class ComplianceRulesDto extends NameAndUuidDto {
    @Schema(description = "Description of the rule", example = "Sample rule description")
    private String description;

    @Schema(description = "Attributes of the rule")
    private List<ResponseAttributeDto> attributes;

    //Default getters and setters
    public List<ResponseAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ResponseAttributeDto> attributes) {
        this.attributes = attributes;
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
                .append("attributes", attributes)
                .append("description", description)
                .toString();
    }
}
