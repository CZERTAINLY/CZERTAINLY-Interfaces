package com.czertainly.api.model.core.compliance;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ComplianceProviderSummaryDto {
    @Schema(description = "Name of the Compliance Provider", required = true)
    private String connectorName;

    @Schema(description = "Number of rules for the Provider")
    private Integer numberOfRules;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", connectorName)
                .append("rules", numberOfRules)
                .toString();
    }
}
