package com.czertainly.api.model.credential;

import com.czertainly.api.model.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

public class CredentialRequestDto implements Serializable {

    @Schema(description = "Credential name",
            required = true)
    private String name;

    @Schema(description = "Credential Kind",
            example = "SoftKeyStore, Basic, ApiKey, etc",
            required = true)
    private String kind;

    @Schema(description = "List of Credential Attributes",
            implementation = List.class,
            required = true)
    private List<RequestAttributeDto> attributes;

    @Schema(description = "UUID of Credential provider Connector",
            required = true)
    private String connectorUuid;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public List<RequestAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<RequestAttributeDto> attributes) {
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
                .append("kind", kind)
                .append("attributes", attributes)
                .append("connectorUuid", connectorUuid)
                .toString();
    }
}
