package com.czertainly.api.model.client.certificate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@Data
public class MultipleCertificateObjectUpdateDto {

    @Schema(
            description = "UUID of the Certificate Group"
    )
    private String groupUuid;

    @Schema(
            description = "Certificate Owner UUID"
    )
    private String ownerUuid;

    @Schema(
            description = "UUID of the RA Profile"
    )
    private String raProfileUuid;

    @Schema(
            description = "List of Certificate UUIDs"
    )
    private List<String> certificateUuids;

    @Schema(
            description = "Certificate filter input"
    )
    private List<SearchFilterRequestDto> filters;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("groupUuid", groupUuid)
                .append("ownerUuid", ownerUuid)
                .append("raProfileUuid", raProfileUuid)
                .append("certificateUuids", certificateUuids)
                .append("filters", filters)
                .toString();
    }
}
