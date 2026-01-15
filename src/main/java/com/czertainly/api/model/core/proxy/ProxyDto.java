package com.czertainly.api.model.core.proxy;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.core.connector.ConnectorDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class ProxyDto extends NameAndUuidDto implements Serializable {

    @Schema(description = "Detailed description of the Proxy",
        examples = {"This proxy is used for connecting to external connectors in DMZ network"},
        requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @Schema(description = "Code of the Proxy",
        examples = {"myproxy123"},
        requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @Schema(description = "Status of the Proxy",
        examples = {"CONNECTED"},
        requiredMode = Schema.RequiredMode.REQUIRED)
    private ProxyStatus status;

    @Schema(description = "List of Connectors associated with the Proxy")
    private List<ConnectorDto> connectors;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("uuid", uuid)
            .append("name", name)
            .append("description", description)
            .append("code", code)
            .append("status", status)
            .append("connectors", connectors)
            .toString();
    }
}
