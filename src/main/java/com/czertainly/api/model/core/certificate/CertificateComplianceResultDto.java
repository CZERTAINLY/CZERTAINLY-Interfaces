package com.czertainly.api.model.core.certificate;

import com.czertainly.api.model.connector.compliance.ComplianceResponseRulesDto;
import com.czertainly.api.model.core.compliance.ComplianceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/*
Contains the list of parameters returned by the Compliance Provider after
checking the certificate for compliance. This response will be received
by the Core from the Connector once the compliance check is completed.
 */
public class CertificateComplianceResultDto {

    @Schema(description = "Name of the Compliance Provider", required = true, example = "Provider1")
    private String connectorName;

    @Schema(description = "UUID of the Compliance Provider", required = true, example = "c35bc88c-d0ef-11ec-9d64-0242ac120003")
    private String connectorUuid;

    @Schema(description = "Compliance status", required = true, example = "compliant")
    private ComplianceStatus status;

    @Schema(description = "List of rules applied and status of each rule")
    private List<ComplianceResponseRulesDto> rules;

    //Default getters and setters

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public String getConnectorUuid() {
        return connectorUuid;
    }

    public void setConnectorUuid(String connectorUuid) {
        this.connectorUuid = connectorUuid;
    }

    public ComplianceStatus getStatus() {
        return status;
    }

    public void setStatus(ComplianceStatus status) {
        this.status = status;
    }

    public List<ComplianceResponseRulesDto> getRules() {
        return rules;
    }

    public void setRules(List<ComplianceResponseRulesDto> rules) {
        this.rules = rules;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("status", status)
                .append("rules", rules)
                .append("connectorName", connectorName)
                .append("connectorUuid", connectorUuid)
                .toString();
    }
}
