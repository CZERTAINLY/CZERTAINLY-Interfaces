package com.czertainly.api.model.client.connector;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.Named;
import com.czertainly.api.model.core.connector.AuthType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class ConnectorRequestDto implements Named {

    @Schema(description = "Name of the Connector",
            examples = {"Connector1"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @Schema(description = "URL of the Connector to connect",
            examples = {"http://network-discovery-provicer:8080"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String url;
    @Schema(description = "Type of authentication for the Connector",
            examples = {"none"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private AuthType authType;
    @Schema(description = "List of authentication Attributes. Required if the authentication type is not NONE",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RequestAttributeDto> authAttributes;
    @Schema(description = "List of Custom Attributes")
    private List<RequestAttributeDto> customAttributes;
    @Schema(description = "Proxy instance ID for message queue routing. " +
            "When set, connector communicates via message queue proxy. " +
            "When null, connector uses direct REST communication.",
            examples = {"proxy1", "proxy-eu-west"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String proxyId;

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

    public List<RequestAttributeDto> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<RequestAttributeDto> customAttributes) {
        this.customAttributes = customAttributes;
    }

    public String getProxyId() {
        return proxyId;
    }

    public void setProxyId(String proxyId) {
        this.proxyId = proxyId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("url", url)
                .append("authType", authType)
                .append("authAttributes", authAttributes)
                .append("customAttributes", customAttributes)
                .append("proxyId", proxyId)
                .toString();
    }
}
