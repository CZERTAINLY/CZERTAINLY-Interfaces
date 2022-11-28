package com.czertainly.api.model.core.compliance;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.client.raprofile.SimplifiedRaProfileDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class ComplianceProfileDto extends NameAndUuidDto {

    @Schema(description = "Description of the Compliance Profile")
    private String description;

    @Schema(description = "List of rules", required = true)
    private List<ComplianceConnectorAndRulesDto> rules;

    @Schema(description = "List of groups", required = true)
    private List<ComplianceConnectorAndGroupsDto> groups;

    @Schema(description = "List of associated RA Profiles")
    private List<SimplifiedRaProfileDto> raProfiles;

    @Schema(description = "List of Custom Attributes")
    private List<ResponseAttributeDto> customAttributes;

    //Default getters and setters

    public List<ComplianceConnectorAndRulesDto> getRules() {
        return rules;
    }

    public void setRules(List<ComplianceConnectorAndRulesDto> rules) {
        this.rules = rules;
    }

    public List<SimplifiedRaProfileDto> getRaProfiles() {
        return raProfiles;
    }

    public void setRaProfiles(List<SimplifiedRaProfileDto> raProfiles) {
        this.raProfiles = raProfiles;
    }

    public List<ComplianceConnectorAndGroupsDto> getGroups() {
        return groups;
    }

    public void setGroups(List<ComplianceConnectorAndGroupsDto> groups) {
        this.groups = groups;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ResponseAttributeDto> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<ResponseAttributeDto> customAttributes) {
        this.customAttributes = customAttributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("rules", rules)
                .append("raProfiles", raProfiles)
                .append("groups", groups)
                .append("description", description)
                .append("customAttributes", customAttributes)
                .toString();
    }
}
