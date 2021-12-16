package com.czertainly.api.model.connector;

import com.czertainly.api.model.AttributeDefinition;
import com.czertainly.api.model.Identified;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

public class ConnectorDto implements Identified, Serializable {

    @Schema(description = "Id of the connector",
            example = "1",
            required = true)
    private Long id;
    @Schema(description = "UUID of the connector",
            example = "204a57f6-2ed3-45b6-bf09-af8b8c900e33",
            required = true)
    private String uuid;
    @Schema(description = "Name of the connector",
            example = "Connector1",
            required = true)
    private String name;
    @Schema(description = "List of function groups implemented by the connector",
            required = true)
    private List<FunctionGroupDto> functionGroups;
    @Schema(description = "URL of the connector",
            example = "http://network-discovery-provider:8080",
            required = true)
    private String url;
    @Schema(description = "Type of authentication for the connector",
            example = "NONE",
            required = true)
    private AuthType authType;
    @Schema(description = "List of attributes for the authentication type",
            required = false)
    private List<AttributeDefinition> authAttributes;
    @Schema(description = "Status of the connector",
            example = "CONNECTED",
            required = true)
    private ConnectorStatus status;

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

    public List<FunctionGroupDto> getFunctionGroups() {
        return functionGroups;
    }

    public void setFunctionGroups(List<FunctionGroupDto> functionGroups) {
        this.functionGroups = functionGroups;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AuthType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }

    public List<AttributeDefinition> getAuthAttributes() {
        return authAttributes;
    }

    public void setAuthAttributes(List<AttributeDefinition> authAttributes) {
        this.authAttributes = authAttributes;
    }

    public ConnectorStatus getStatus() {
        return status;
    }

    public void setStatus(ConnectorStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("uuid", uuid)
                .append("name", name)
                .append("functionGroups", functionGroups)
                .append("url", url)
                .append("authType", authType)
                .append("authAttributes", authAttributes)
                .append("status", status)
                .toString();
    }
}
