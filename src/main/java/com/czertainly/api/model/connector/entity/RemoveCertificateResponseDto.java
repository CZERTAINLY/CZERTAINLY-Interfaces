package com.czertainly.api.model.connector.entity;

import com.czertainly.api.model.common.attribute.v2.InfoAttribute;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class RemoveCertificateResponseDto {

    @Schema(
            description = "Metadata of the Certificate",
            required = true
    )
    private List<InfoAttribute> certificateMetadata;

    public List<InfoAttribute> getCertificateMetadata() {
        return certificateMetadata;
    }

    public void setCertificateMetadata(List<InfoAttribute> certificateMetadata) {
        this.certificateMetadata = certificateMetadata;
    }
}
