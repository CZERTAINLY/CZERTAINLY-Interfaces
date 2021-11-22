package com.czertainly.api.model.connector;

import com.czertainly.api.model.AttributeDefinition;
import com.czertainly.api.model.Identified;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

public class ConnectorDto implements Identified, Serializable {

    private Long id;
    private String uuid;
    private String name;
    private List<FunctionGroupDto> functionGroups;
    private String url;
    private AuthType authType;
    private List<AttributeDefinition> authAttributes;
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
