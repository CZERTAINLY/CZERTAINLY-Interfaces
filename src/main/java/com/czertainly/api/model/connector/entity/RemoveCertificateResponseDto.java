package com.czertainly.api.model.connector.entity;

import com.czertainly.api.model.common.attribute.v2.MetadataAttribute;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class RemoveCertificateResponseDto {

    @Schema(
            description = "Metadata of the Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<MetadataAttribute> certificateMetadata;

    public List<MetadataAttribute> getCertificateMetadata() {
        return certificateMetadata;
    }

    public void setCertificateMetadata(List<MetadataAttribute> certificateMetadata) {
        this.certificateMetadata = certificateMetadata;
    }
}
