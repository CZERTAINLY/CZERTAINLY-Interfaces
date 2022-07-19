package com.czertainly.api.model.connector.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

public class PushCertificateResponseDto {

    @Schema(
            description = "Certificate metadata"
    )
    private Map<String, Object> certificateMetadata;

    public Map<String, Object> getCertificateMetadata() {
        return certificateMetadata;
    }

    public void setCertificateMetadata(Map<String, Object> certificateMetadata) {
        this.certificateMetadata = certificateMetadata;
    }
}
