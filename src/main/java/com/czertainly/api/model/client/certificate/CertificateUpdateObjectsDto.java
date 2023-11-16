package com.czertainly.api.model.client.certificate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
public class CertificateUpdateObjectsDto {

    @Schema(description = "Group UUID")
    private String groupUuid;

    @Schema(description = "Certificate Owner UUID")
    private String ownerUuid;

    @Schema(description = "RA Profile UUID")
    private String raProfileUuid;

    @Schema(description = "Mark CA certificate as trusted")
    private Boolean trustedCa;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("groupUuid", groupUuid)
                .append("ownerUuid", ownerUuid)
                .append("raProfileUuid", raProfileUuid)
                .append("trustedCa", trustedCa)
                .toString();
    }
}
