package com.czertainly.api.model.client.authority;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class AuthorityInstanceRequestDto {

    @Schema(description = "Authority instance name",
            required = true)
    private String name;

    @Schema(description = "List of Authority instance Attributes",
            required = true)
    private List<RequestAttributeDto> attributes;

    @Schema(description = "UUID of Authority provider",
            required = true)
    private String connectorUuid;

    @Schema(description = "Authority instance Kind",
            example = "LegacyEjbca, ADCS, etc",
            required = true)
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("attributes", attributes)
                .append("connectorUuid", connectorUuid)
                .append("kind", kind)
                .toString();
    }
}
