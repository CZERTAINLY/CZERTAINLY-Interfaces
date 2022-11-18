package com.czertainly.api.model.connector.entity;

import com.czertainly.api.model.common.attribute.v2.InfoAttribute;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

public class PushCertificateResponseDto {

    @Schema(
            description = "Certificate metadata"
    )
    private List<InfoAttribute> certificateMetadata;

    @Schema(description = "Is private key available for the certificate in location")
    private Boolean withKey;

    public boolean isWithKey() {
        return withKey;
    }

    public void setWithKey(boolean hasPrivateKey) {
        this.withKey = hasPrivateKey;
    }

    public List<InfoAttribute> getCertificateMetadata() {
        return certificateMetadata;
    }

    public void setCertificateMetadata(List<InfoAttribute> certificateMetadata) {
        this.certificateMetadata = certificateMetadata;
    }
}
