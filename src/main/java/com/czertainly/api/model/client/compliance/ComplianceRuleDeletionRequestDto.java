package com.czertainly.api.model.client.compliance;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ComplianceRuleDeletionRequestDto {

    @Schema(description = "UUID of the Compliance Provider", required = true, example = "1212a-34dddf34-4334f-34ddfvfdg1y3")
    private String connectorUuid;

    @Schema(description = "Kind of the Compliance Provider", required = true, example = "default")
    private String kind;

    @Schema(description = "UUID of the rule", required = true, example = "1212a-34dddf34-4334f-34ddfvfdg1y3")
    private String ruleUuid;

    public String getConnectorUuid() {
        return connectorUuid;
    }

    public void setConnectorUuid(String connectorUuid) {
        this.connectorUuid = connectorUuid;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getRuleUuid() {
        return ruleUuid;
    }

    public void setRuleUuid(String ruleUuid) {
        this.ruleUuid = ruleUuid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("connectorUuid", connectorUuid)
                .append("connectorKind", kind)
                .append("ruleUuid", ruleUuid)
                .toString();
    }
}
