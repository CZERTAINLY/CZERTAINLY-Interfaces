package com.czertainly.api.model.connector.entity;

import com.czertainly.api.model.common.attribute.v2.InfoAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class LocationDetailResponseDto {

    @Schema(
            description = "List of Certificates in the Location",
            required = true
    )
    private List<CertificateLocationDto> certificates;

    @Schema(
            description = "Location metadata",
            required = false
    )
    private List<InfoAttribute> metadata;

    @Schema(
            description = "Support for multiple Certificates in the Location",
            defaultValue = "false",
            required = true
    )
    private boolean multipleEntries;

    @Schema(
            description = "Support for key pair management in the Location",
            defaultValue = "false",
            required = true
    )
    private boolean supportKeyManagement;

    public List<CertificateLocationDto> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<CertificateLocationDto> certificates) {
        this.certificates = certificates;
    }

    public List<InfoAttribute> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<InfoAttribute> metadata) {
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
