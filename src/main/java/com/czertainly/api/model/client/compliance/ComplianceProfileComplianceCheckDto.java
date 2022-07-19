package com.czertainly.api.model.client.compliance;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class ComplianceProfileComplianceCheckDto {
    @Schema(description = "List of UUIDs of the Compliance Profiles")
    private List<String> complianceProfileUuids;

    public List<String> getComplianceProfileUuids() {
        return complianceProfileUuids;
    }

    public void setComplianceProfileUuids(List<String> complianceProfileUuids) {
        this.complianceProfileUuids = complianceProfileUuids;
    }

    @Override
    public String toString() {
        return "ComplianceProfileComplianceCheckDto{" +
                "complianceProfileUuids=" + complianceProfileUuids +
                '}';
    }
}
