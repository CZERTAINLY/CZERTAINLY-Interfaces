package com.czertainly.api.model.client.entity;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class EntityInstanceRequestDto {

    @Schema(description = "Entity instance name",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "List of Entity instance Attributes",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<RequestAttributeDto> attributes;

    @Schema(description = "List of Custom Attributes")
    private List<RequestAttributeDto> customAttributes;

    @Schema(description = "UUID of Entity Provider",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String connectorUuid;

    @Schema(description = "Entity instance Kind",
            examples = {"Keystore, etc."},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String kind;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RequestAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<RequestAttributeDto> attributes) {
        this.attributes = attributes;
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

    public List<RequestAttributeDto> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<RequestAttributeDto> customAttributes) {
        this.customAttributes = customAttributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("attributes", attributes)
                .append("customAttributes", customAttributes)
                .append("connectorUuid", connectorUuid)
                .append("kind", kind)
                .toString();
    }
}
