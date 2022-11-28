package com.czertainly.api.model.client.compliance;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class ComplianceProfileRequestDto {
    @Schema(description = "Name of the Compliance Profile", required = true, example = "Profile 1")
    private String name;

    @Schema(description = "Description of the Compliance Profile", example = "Profile 1")
    private String description;

    @Schema(description = "Rules to be associated with the Compliance Profile. Profiles can be created without rules and can be added later")
    private List<ComplianceProfileRulesRequestDto> rules;

    @Schema(description = "List of Custom Attributes")
    private List<RequestAttributeDto> customAttributes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ComplianceProfileRulesRequestDto> getRules() {
        return rules;
    }

    public void setRules(List<ComplianceProfileRulesRequestDto> rules) {
        this.rules = rules;
    }

    public List<RequestAttributeDto> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<RequestAttributeDto> customAttributes) {
        this.customAttributes = customAttributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("description", description)
                .append("rules", rules)
                .append("customAttributes", customAttributes)
                .toString();
    }
}
