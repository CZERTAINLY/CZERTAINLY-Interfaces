package com.czertainly.api.model.connector.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

public class PushCertificateResponseDto {

    @Schema(
            description = "Certificate metadata"
    )
    private Map<String, Object> certificateMetadata;

    @Schema(description = "Is private key available for the certificate in location")
    private Boolean withKey;

    public boolean isWithKey() {
        return withKey;
    }

    public void setWithKey(boolean hasPrivateKey) {
        this.withKey = hasPrivateKey;
    }

    public Map<String, Object> getCertificateMetadata() {
        return certificateMetadata;
    }

    public void setCertificateMetadata(Map<String, Object> certificateMetadata) {
        this.certificateMetadata = certificateMetadata;
    }
}
