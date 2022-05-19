package com.czertainly.api.model.client.compliance;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class ComplianceProfileRequestDto {
    @Schema(description = "Name of the Compliance Profile", required = true, example = "Profile 1")
    private String name;

    @Schema(description = "Description of the Compliance Profile", example = "Profile 1")
    private String description;

    @Schema(description = "List of rules and groups")
    private List<ComplianceProfileRulesRequestDto> rules;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ComplianceProfileRulesRequestDto> getRules() {
        return rules;
    }

    public void setRules(List<ComplianceProfileRulesRequestDto> rules) {
        this.rules = rules;
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
                .append("name", name)
                .append("rules", rules)
                .append("description", description)
                .toString();
    }
}
