package com.czertainly.api.model.client.connector;

import com.czertainly.api.model.commons.RequestAttributeDto;
import com.czertainly.api.model.core.connectors.AuthType;
import io.swagger.v3.oas.annotations.media.Schema;

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
    private List<RequestAttributeDto> authAttributes;

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

    public List<RequestAttributeDto> getAuthAttributes() {
        return authAttributes;
    }

    public void setAuthAttributes(List<RequestAttributeDto> authAttributes) {
        this.authAttributes = authAttributes;
    }
}
