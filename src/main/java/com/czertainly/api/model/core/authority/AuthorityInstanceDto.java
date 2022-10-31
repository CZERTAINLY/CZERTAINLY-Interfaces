package com.czertainly.api.model.core.authority;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class AuthorityInstanceDto extends NameAndUuidDto {

    @Schema(description = "List of Authority instance Attributes",
            required = true)
    private List<ResponseAttributeDto> attributes;

    @Schema(description = "Status of Authority instance",
            required = true)
    private String status;

    @Schema(description = "UUID of Authority provider",
            required = true)
    private String connectorUuid;

    @Schema(description = "Name of Authority provider",
            required = true)
    private String connectorName;

    @Schema(description = "Authority Instance Kind",
            example = "LegacyEjbca, ADCS, etc.",
            required = true)
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("attributes", attributes)
                .append("status", status)
                .append("connectorUuid", connectorUuid)
                .append("connectorName", connectorName)
                .append("kind", kind)
                .toString();
    }
}
