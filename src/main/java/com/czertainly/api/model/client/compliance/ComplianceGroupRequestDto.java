package com.czertainly.api.model.client.compliance;

import com.czertainly.api.model.common.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class ComplianceGroupRequestDto {

    @Schema(description = "UUID of the Compliance Provider", required = true, example = "1212a-34dddf34-4334f-34ddfvfdg1y3")
    private String connectorUuid;

    @Schema(description = "Kind of the Compliance Provider", required = true, example = "default")
    private String kind;

    @Schema(description = "UUID of the group", required = true, example = "1212a-34dddf34-4334f-34ddfvfdg1y3")
    private String groupUuid;

    public String getConnectorUuid() {
        return connectorUuid;
    }

    public void setConnectorUuid(String connectorUuid) {
        this.connectorUuid = connectorUuid;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("connectorUuid", connectorUuid)
                .append("connectorKind", kind)
                .append("groupUuid", groupUuid)
                .toString();
    }
}
