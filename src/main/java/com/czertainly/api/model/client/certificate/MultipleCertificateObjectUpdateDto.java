package com.czertainly.api.model.client.certificate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@Data
public class MultipleCertificateObjectUpdateDto {

    @Schema(description = "Certificate Groups UUIDs (set to empty list to remove certificate from all groups)")
    private List<String> groupUuids;

    @Schema(description = "Certificate owner user UUID (set to empty string to remove owner of certificate)")
    private String ownerUuid;

    @Schema(description = "RA Profile UUID (set to empty string to remove certificate from RA profile)")
    private String raProfileUuid;

    @Schema(description = "List of Certificate UUIDs")
    private List<String> certificateUuids;

    @Schema(description = "Certificate filter input")
    private List<SearchFilterRequestDto> filters;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("groupUuids", groupUuids).append("ownerUuid", ownerUuid).append("raProfileUuid", raProfileUuid).append("certificateUuids", certificateUuids).append("filters", filters).toString();
    }
}
