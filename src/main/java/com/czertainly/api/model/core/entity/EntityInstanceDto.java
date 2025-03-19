package com.czertainly.api.model.core.entity;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

public class EntityInstanceDto extends NameAndUuidDto {

    @Schema(description = "List of Entity instance Attributes",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ResponseAttributeDto> attributes = new ArrayList<>();

    @Schema(description = "List of Custom Attributes")
    private List<ResponseAttributeDto> customAttributes;

    @Schema(description = "Status of Entity instance",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String status;

    @Schema(description = "UUID of Entity Provider",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String connectorUuid;

    @Schema(description = "Name of Entity Provider",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String connectorName;

    @Schema(description = "Entity instance Kind",
            examples = {"Keystore, etc."},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String kind;

    public List<ResponseAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ResponseAttributeDto> attributes) {
        this.attributes = attributes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getConnectorName() { return connectorName; }

    public void setConnectorName(String connectorName) { this.connectorName = connectorName; }

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
                .append("attributes", attributes)
                .append("customAttributes", customAttributes)
                .append("status", status)
                .append("connectorUuid", connectorUuid)
                .append("connectorName", connectorName)
                .append("kind", kind)
                .toString();
    }
}
