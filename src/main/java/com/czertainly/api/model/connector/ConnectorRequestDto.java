package com.czertainly.api.model.connector;

import com.czertainly.api.model.AttributeDefinition;
import com.czertainly.api.model.Identified;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

public class ConnectorRequestDto {

    @Schema(description = "Name of the Connector",
            example = "Connector1",
            required = true)
    private String name;
    @Schema(description = "URL of the Connector to connect",
            example = "http://network-discovery-provicer:8080",
            required = true)
    private String url;
    @Schema(description = "Type of authentication for the Connector",
            example = "NONE",
            required = true)
    private AuthType authType;
    @Schema(description = "List of authentication Attributes. Required if the authentication type is not NONE",
            required = false)
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
