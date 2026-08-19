package com.otilm.api.model.client.certificate;

import com.otilm.api.model.client.attribute.RequestAttribute;
import com.otilm.api.model.core.logging.Loggable;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
public class MultipleCertificateObjectUpdateDto implements Loggable {

    @Schema(description = "Certificate Groups UUIDs (set to empty list to remove certificate from all groups)")
    private List<String> groupUuids;

    @Schema(description = "Certificate owner user UUID (set to empty string to remove owner of certificate)")
    private String ownerUuid;

    @Schema(description = "RA Profile UUID (set to empty string to remove certificate from RA profile)")
    private String raProfileUuid;

    @Schema(description = "Identify-operation dynamic attributes, as listed by "
            + "GET /v1/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/attributes/identify for the target "
            + "RA profile. Used only when raProfileUuid assigns a new RA profile, which identifies each certificate "
            + "at its authority. Applies to every certificate in the batch: the request carries a single "
            + "raProfileUuid, so one authority and one identify schema govern the whole update. Optional — an "
            + "authority that offers no identify schema needs none.")
    private List<RequestAttribute> attributes;

    @Schema(description = "List of Certificate UUIDs")
    private List<String> certificateUuids;

    @Schema(description = "Certificate filter input")
    private List<SearchFilterRequestDto> filters;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("groupUuids", groupUuids)
                .append("ownerUuid", ownerUuid)
                .append("raProfileUuid", raProfileUuid)
                .append("certificateUuids", certificateUuids)
                .append("filters", filters)
                .toString();
    }

    @Override
    public Serializable toLogData() {
        return null;
    }

    @Override
    public List<String> toLogResourceObjectsNames() {
        return List.of();
    }

    @Override
    public List<UUID> toLogResourceObjectsUuids() {
        return certificateUuids.stream().map(UUID::fromString).toList();
    }
}
