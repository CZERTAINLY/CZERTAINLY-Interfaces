package com.czertainly.api.model.connector;

import com.czertainly.api.model.AttributeDefinition;
import com.czertainly.api.model.Identified;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

public class ConnectorRequestDto {

    private String name;
    private String url;
    private AuthType authType;
    private List<AttributeDefinition> authAttributes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
