package com.czertainly.api.model.client.connector;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.core.connector.AuthType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class ConnectorUpdateRequestDto {

    @Schema(description = "URL of the Connector to connect",
            examples = {"http://network-discovery-provicer:8080"})
    private String url;
    @Schema(description = "Type of authentication for the Connector",
            examples = {"none"})
    private AuthType authType;
    @Schema(description = "List of authentication Attributes. Required if the authentication type is not NONE")
    private List<RequestAttributeDto> authAttributes;
    @Schema(description = "List of Custom Attributes")
    private List<RequestAttributeDto> customAttributes;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("url", url)
                .append("authType", authType)
                .append("authAttributes", authAttributes)
                .append("customAttributes", customAttributes)
                .toString();
    }
}
