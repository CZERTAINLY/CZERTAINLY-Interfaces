package com.czertainly.api.model.client.compliance;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.core.certificate.CertificateType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

public class ComplianceProfileRuleDto extends NameAndUuidDto {

    @Schema(description = "Description of the rule", example = "Sample rule description")
    private String description;

    @Schema(description = "Connector Name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String connectorName;

    @Schema(description = "Connector UUID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String connectorUuid;

    @Schema(description = "Connector Kind", requiredMode = Schema.RequiredMode.REQUIRED)
    private String kind;

    @Schema(description = "Group UUID")
    private String groupUuid;

    @Schema(description = "Certificate type for the rule", requiredMode = Schema.RequiredMode.REQUIRED, example = "X509")
    private CertificateType certificateType;

    @Schema(description = "List of attributes for the rule", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ResponseAttributeDto> attributes = new ArrayList<>();

    @Schema(description = "UUID of the Compliance Profile", requiredMode = Schema.RequiredMode.REQUIRED)
    private String complianceProfileUuid;

    @Schema(description = "Name of the Compliance Profile", requiredMode = Schema.RequiredMode.REQUIRED)
    private String complianceProfileName;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CertificateType getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
    }

    public List<ResponseAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ResponseAttributeDto> attributes) {
        this.attributes = attributes;
    }

    public String getComplianceProfileUuid() {
        return complianceProfileUuid;
    }

    public void setComplianceProfileUuid(String complianceProfileUuid) {
        this.complianceProfileUuid = complianceProfileUuid;
    }

    public String getComplianceProfileName() {
        return complianceProfileName;
    }

    public void setComplianceProfileName(String complianceProfileName) {
        this.complianceProfileName = complianceProfileName;
    }

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

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("description", description)
                .append("connectorName", connectorName)
                .append("connectorUuid", connectorUuid)
                .append("kind", kind)
                .append("groupUuid", groupUuid)
                .append("certificateType", certificateType)
                .append("attributes", attributes)
                .append("complianceProfileUuid", complianceProfileUuid)
                .append("complianceProfileName", complianceProfileName)
                .append("uuid", uuid)
                .append("name", name)
                .toString();
    }
}
