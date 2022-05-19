package com.czertainly.api.model.core.compliance;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.ResponseAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class ComplianceConnectorAndRulesDto {
    @Schema(description = "Name of the Compliance Provider")
    private String name;

    @Schema(description = "UUID of the Compliance Provider")
    private String uuid;

    @Schema(description = "Rules associated")
    private List<ComplianceRulesDto> rules;

    //Default getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<ComplianceRulesDto> getRules() {
        return rules;
    }

    public void setRules(List<ComplianceRulesDto> rules) {
        this.rules = rules;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name",name)
                .append("rules", rules)
                .toString();
    }
}
