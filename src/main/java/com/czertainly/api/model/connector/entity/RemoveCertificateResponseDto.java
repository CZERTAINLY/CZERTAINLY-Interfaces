package com.czertainly.api.model.connector.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

public class RemoveCertificateResponseDto {

    @Schema(
            description = "Metadata of the Certificate",
            required = true
    )
    private Map<String, Object> certificateMetadata;

    public Map<String, Object> getCertificateMetadata() {
        return certificateMetadata;
    }

    public void setCertificateMetadata(Map<String, Object> certificateMetadata) {
        this.certificateMetadata = certificateMetadata;
    }
}
