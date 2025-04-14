package com.czertainly.api.model.connector.compliance;

import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.core.certificate.CertificateType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/*
List of rules information from the Compliance Provider. These rules will
have name, uuid and the attributes. The attributes of the rules is used
to request for additional information for the rule.
 */
public class ComplianceRulesResponseDto {
    @Schema(description = "UUID of the rule", requiredMode = Schema.RequiredMode.REQUIRED, examples = {"166b5cf52-63f2-11ec-90d6-0242ac120003"})
    private String uuid;

    @Schema(description = "UUID of the group to which the rule belongs to", examples = {"166b5cf52-63f2-11ec-90d6-0242ac120003"})
    private String groupUuid;

    @Schema(description = "Name of the rule", requiredMode = Schema.RequiredMode.REQUIRED, examples = {"Rule1"})
    private String name;

    @Schema(description = "Type of the certificate to which this rule can be applied", requiredMode = Schema.RequiredMode.REQUIRED, examples = {"X509"})
    private CertificateType certificateType;

    @Schema(description = "Rule attributes")
    private List<BaseAttribute> attributes;

    @Schema(description = "Description of the rule", examples = {"Sample rule description"})
    private String description;

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

    public List<BaseAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<BaseAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }

    public CertificateType getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("attributes", attributes)
                .append("description", description)
                .append("groupUuid", groupUuid)
                .append("certificateType", certificateType)
                .toString();
    }
}
