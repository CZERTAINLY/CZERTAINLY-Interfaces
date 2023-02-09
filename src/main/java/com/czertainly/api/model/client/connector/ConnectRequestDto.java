package com.czertainly.api.model.client.connector;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.core.connector.AuthType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class ConnectRequestDto {

    @Schema(description = "URL of the Connector to connect",
            example = "http://network-discovery-provicer:8080",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String url;
    @Schema(description = "UUID of the Connector. Mandatory if connection is needed for the same Connector")
    private String uuid;
    @Schema(description = "Type of authentication for the Connector",
            example = "none",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private AuthType authType;
    @Schema(description = "List of authentication Attributes. Required if the authentication type is not NONE",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RequestAttributeDto> authAttributes;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
