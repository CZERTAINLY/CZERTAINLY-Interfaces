package com.czertainly.api.model.connector.entity;

import com.czertainly.api.model.common.attribute.v2.MetadataAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class LocationDetailResponseDto {

    @Schema(
            description = "List of Certificates in the Location",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<CertificateLocationDto> certificates;

    @Schema(
            description = "Location metadata",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private List<MetadataAttribute> metadata;

    @Schema(
            description = "Support for multiple Certificates in the Location",
            defaultValue = "false",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean multipleEntries;

    @Schema(
            description = "Support for key pair management in the Location",
            defaultValue = "false",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean supportKeyManagement;

    public List<CertificateLocationDto> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<CertificateLocationDto> certificates) {
        this.certificates = certificates;
    }

    public List<MetadataAttribute> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<MetadataAttribute> metadata) {
        this.metadata = metadata;
    }

    public boolean isMultipleEntries() {
        return multipleEntries;
    }

    public void setMultipleEntries(boolean multipleEntries) {
        this.multipleEntries = multipleEntries;
    }

    public boolean isSupportKeyManagement() {
        return supportKeyManagement;
    }

    public void setSupportKeyManagement(boolean supportKeyManagement) {
        this.supportKeyManagement = supportKeyManagement;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("metadata", metadata)
                .append("multipleEntries", multipleEntries)
                .append("supportKeyManagement", supportKeyManagement)
                .toString();
    }
}
