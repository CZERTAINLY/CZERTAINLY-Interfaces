package com.czertainly.api.model.credential;

import com.czertainly.api.model.AttributeDefinition;
import com.czertainly.api.model.Identified;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

public class CredentialRequestDto implements Serializable {

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

    @Schema(description = "UUID of credential provider connector",
            required = true)
    private String connectorUuid;


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

    public String  getConnectorUuid() {
        return connectorUuid;
    }

    public void setConnectorUuid(String connectorUuid) {
        this.connectorUuid = connectorUuid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("credentialType", credentialType)
                .append("attributes", attributes)
                .append("connectorUuid", connectorUuid)
                .toString();
    }
}
