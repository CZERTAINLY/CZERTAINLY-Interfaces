package com.czertainly.api.model.client.certificate;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class MultipleCertificateObjectUpdateDto {

    @Schema(
            description = "UUID of the RA Profile"
    )
    private String raProfileUuid;

    @Schema(
            description = "UUID of the Certificate Group"
    )
    private String groupUuid;

    @Schema(
            description = "Certificate Owner UUID"
    )
    private String ownerUuid;

    @Schema(
            description = "List of Certificate UUIDs"
    )
    private List<String> certificateUuids;

    @Schema(
            description = "Certificate filter input"
    )
    private List<SearchFilterRequestDto> filters;

    public List<String> getCertificateUuids() {
        return certificateUuids;
    }

    public void setCertificateUuids(List<String> certificateUuids) {
        this.certificateUuids = certificateUuids;
    }

    public List<SearchFilterRequestDto> getFilters() {
        return filters;
    }

    public void setFilters(List<SearchFilterRequestDto> filters) {
        this.filters = filters;
    }

    public String getRaProfileUuid() {
        return raProfileUuid;
    }

    public void setRaProfileUuid(String raProfileUuid) {
        this.raProfileUuid = raProfileUuid;
    }

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }

    public String getOwnerUuid() {
        return ownerUuid;
    }

    public void setOwnerUuid(String ownerUuid) {
        this.ownerUuid = ownerUuid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("raProfileUuid", raProfileUuid)
                .append("groupUuid", groupUuid)
                .append("ownerUuid", ownerUuid)
                .append("certificateUuids", certificateUuids)
                .append("filters", filters)
                .toString();
    }
}
