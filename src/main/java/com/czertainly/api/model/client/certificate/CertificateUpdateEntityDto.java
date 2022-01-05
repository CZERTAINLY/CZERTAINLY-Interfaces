package com.czertainly.api.model.client.certificate;

import io.swagger.v3.oas.annotations.media.Schema;

public class CertificateUpdateEntityDto {

    @Schema(description = "Entity UUID",
            required = true)
    private String entityUuid;

    public String getEntityUuid() {
        return entityUuid;
    }

    public void setEntityUuid(String entityUuid) {
        this.entityUuid = entityUuid;
    }

    public CertificateUpdateEntityDto() {
    }
}
