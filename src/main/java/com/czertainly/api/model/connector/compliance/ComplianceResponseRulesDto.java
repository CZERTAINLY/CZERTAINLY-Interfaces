package com.czertainly.api.model.connector.compliance;

import com.czertainly.api.model.core.compliance.ComplianceRuleStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/*
Contains the description for rules and its validation results. This object is part of
the ComplianceResponseDto and describes in detail the list of rules applied and their
individual status.
 */
public class ComplianceResponseRulesDto {
    @Schema(description = "UUID of the rule", required = true, example = "166b5cf52-63f2-11ec-90d6-0242ac120003")
    private String uuid;

    @Schema(description = "Name of the rule", required = true, example = "Rule1")
    private String name;

    @Schema(description = "Rule status", required = true, example = "ok")
    private ComplianceRuleStatus status;

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

    public ComplianceRuleStatus getStatus() {
        return status;
    }

    public void setStatus(ComplianceRuleStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("status", status)
                .toString();
    }
}
