package com.czertainly.api.model.credential;

import com.czertainly.api.model.AttributeDefinition;
import com.czertainly.api.model.Identified;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

public class CredentialDto implements Identified, Serializable {

    @Schema(description = "Identifier of credential")
    private Long id;

    @Schema(description = "UUID of credential")
    private String uuid;

    @Schema(description = "Credential name",
            required = true)
    private String name;

    @Schema(description = "Credential type",
            example = "SoftKeyStore, Basic, ApiKey, etc",
            required = true)
    private String credentialType;

    @Schema(description = "List of credential attributes",
            implementation = List.class,
            required = true)
    private List<AttributeDefinition> attributes;

    @Schema(description = "Enabled flag - true = enabled; false = disabled",
            required = true)
    private Boolean enabled;

    @Schema(description = "UUID of credential provider connector",
            required = true)
    private String connectorUuid;

    @Schema(description = "Name of credential provider connector",
            required = true)
    private String connectorName;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
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

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
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
                .append("id", id)
                .append("uuid", uuid)
                .append("name", name)
                .append("credentialType", credentialType)
                .append("attributes", attributes)
                .append("enabled", enabled)
                .append("connectorUuid", connectorUuid)
                .append("connectorName", connectorName)
                .toString();
    }
}
