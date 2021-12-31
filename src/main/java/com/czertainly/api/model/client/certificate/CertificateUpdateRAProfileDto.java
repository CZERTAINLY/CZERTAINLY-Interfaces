package com.czertainly.api.model.client.certificate;

import io.swagger.v3.oas.annotations.media.Schema;

public class CertificateUpdateRAProfileDto {

    @Schema(description = "RA Profile UUID",
            required = true)
    private String raProfileUuid;

    public String getRaProfileUuid() {
        return raProfileUuid;
    }

    public void setRaProfileUuid(String raProfileUuid) {
        this.raProfileUuid = raProfileUuid;
    }

    public CertificateUpdateRAProfileDto() {
    }
}
