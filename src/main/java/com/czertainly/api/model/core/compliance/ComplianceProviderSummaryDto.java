package com.czertainly.api.model.core.compliance;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ComplianceProviderSummaryDto {
    @Schema(description = "Name of the Compliance Provider", requiredMode = Schema.RequiredMode.REQUIRED)
    private String connectorName;

    @Schema(description = "Number of rules for the Provider")
    private Integer numberOfRules;

    @Schema(description = "Number of groups for the Provider")
    private Integer numberOfGroups;

    //Default getters and setters
    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public Integer getNumberOfRules() {
        return numberOfRules;
    }

    public void setNumberOfRules(Integer numberOfRules) {
        this.numberOfRules = numberOfRules;
    }

    public Integer getNumberOfGroups() {
        return numberOfGroups;
    }

    public void setNumberOfGroups(Integer numberOfGroups) {
        this.numberOfGroups = numberOfGroups;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", connectorName)
                .append("rules", numberOfRules)
                .append("groups", numberOfGroups)
                .toString();
    }
}
