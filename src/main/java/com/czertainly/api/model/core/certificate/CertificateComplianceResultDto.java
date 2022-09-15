package com.czertainly.api.model.core.certificate;

import com.czertainly.api.model.common.attribute.ResponseAttributeDto;
import com.czertainly.api.model.connector.compliance.ComplianceResponseRulesDto;
import com.czertainly.api.model.core.compliance.ComplianceRuleStatus;
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

    @Schema(description = "Name of the rule",required = true, example = "RuleName")
    private String ruleName;

    @Schema(description = "Description of the rule",required = true, example = "Description sample")
    private String ruleDescription;

    @Schema(description = "Status of the rule",required = true, example = "nok")
    private ComplianceRuleStatus status;

    @Schema(description = "Attributes of the rule")
    private List<ResponseAttributeDto> attributes;

    //Default getters and setters

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }


    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public ComplianceRuleStatus getStatus() {
        return status;
    }

    public void setStatus(ComplianceRuleStatus status) {
        this.status = status;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }

    public List<ResponseAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ResponseAttributeDto> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("ruleName", ruleName)
                .append("connectorName", connectorName)
                .append("status", status)
                .append("ruleDescription", ruleDescription)
                .append("attributes", attributes)
                .toString();
    }
}
