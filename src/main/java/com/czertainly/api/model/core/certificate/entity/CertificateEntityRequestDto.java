package com.czertainly.api.model.core.certificate.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public class CertificateEntityRequestDto {

    @Schema(description = "Name of the Entity",
            required = true)
    private String name;

    @Schema(description = "Description of the Entity")
    private String description;

    @Schema(description = "Type of the Entity",
            required = true)
    private CertificateEntityCode entityType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CertificateEntityCode getEntityType() {
        return entityType;
    }

    public void setEntityType(CertificateEntityCode entityType) {
        this.entityType = entityType;
    }
}
