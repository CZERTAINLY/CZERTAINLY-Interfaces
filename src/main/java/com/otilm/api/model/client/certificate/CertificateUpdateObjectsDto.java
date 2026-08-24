package com.otilm.api.model.client.certificate;

import com.otilm.api.model.client.attribute.RequestAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
public class CertificateUpdateObjectsDto {

    @Schema(description = "Certificate Groups UUIDs (set to empty list to remove certificate from all groups)")
    private List<String> groupUuids;

    @Schema(description = "Certificate owner user UUID (set to empty string to remove owner of certificate)")
    private String ownerUuid;

    @Schema(description = "RA Profile UUID (set to empty string to remove certificate from RA profile)")
    private String raProfileUuid;

    @Schema(description = "Identify-operation dynamic attributes, as listed by "
            + "GET /v1/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/attributes/identify for the target "
            + "RA profile. Used only when raProfileUuid assigns a new RA profile, which identifies the certificate "
            + "at its authority. Optional — an authority that offers no identify schema needs none.")
    private List<RequestAttribute> attributes;

    @Schema(description = "Mark CA certificate as trusted")
    private Boolean trustedCa;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("groupUuid", groupUuids)
                .append("ownerUuid", ownerUuid)
                .append("raProfileUuid", raProfileUuid)
                .append("trustedCa", trustedCa)
                .toString();
    }
}
