package com.czertainly.api.model.credential;

import com.czertainly.api.model.AttributeDefinition;
import com.czertainly.api.model.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

public class CredentialDto extends NameAndUuidDto implements Serializable {

    @Schema(description = "Credential Kind",
            example = "SoftKeyStore, Basic, ApiKey, etc",
            required = true)
    private String kind;

    @Schema(description = "List of Credential Attributes",
            implementation = List.class,
            required = true)
    private List<AttributeDefinition> attributes;

    @Schema(description = "Enabled flag - true = enabled; false = disabled",
            required = true)
    private Boolean enabled;

    @Schema(description = "UUID of Credential provider Connector",
            required = true)
    private String connectorUuid;

    @Schema(description = "Name of Credential provider Connector",
            required = true)
    private String connectorName;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public List<AttributeDefinition> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDefinition> attributes) {
        this.attributes = attributes;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String  getConnectorUuid() {
        return connectorUuid;
    }

    public void setConnectorUuid(String connectorUuid) {
        this.connectorUuid = connectorUuid;
    }

    public void setConnectorName(String connectorName) { this.connectorName = connectorName; }

    public String getConnectorName() { return connectorName; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("kind", kind)
                .append("attributes", attributes)
                .append("enabled", enabled)
                .append("connectorUuid", connectorUuid)
                .append("connectorName", connectorName)
                .toString();
    }
}
