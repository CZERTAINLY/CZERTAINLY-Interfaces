package com.czertainly.api.model.core.connector;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.ResponseAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

public class ConnectorDto extends NameAndUuidDto implements Serializable {

    @Schema(description = "List of Function Groups implemented by the Connector",
            required = true)
    private List<FunctionGroupDto> functionGroups;
    @Schema(description = "URL of the Connector",
            example = "http://network-discovery-provider:8080",
            required = true)
    private String url;
    @Schema(description = "Type of authentication for the Connector",
            example = "none",
            required = true)
    private AuthType authType;
    @Schema(description = "List of Attributes for the authentication type",
            required = false)
    private List<ResponseAttributeDto> authAttributes;
    @Schema(description = "Status of the Connector",
            example = "CONNECTED",
            required = true)
    private ConnectorStatus status;

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

    public List<ResponseAttributeDto> getAuthAttributes() {
        return authAttributes;
    }

    public void setAuthAttributes(List<ResponseAttributeDto> authAttributes) {
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
                .append("functionGroups", functionGroups)
                .append("url", url)
                .append("authType", authType)
                .append("authAttributes", authAttributes)
                .append("status", status)
                .append("name", name)
                .append("uuid", uuid)
                .toString();
    }
}
