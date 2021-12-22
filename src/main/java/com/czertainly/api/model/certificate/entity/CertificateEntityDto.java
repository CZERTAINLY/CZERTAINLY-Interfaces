package com.czertainly.api.model.certificate.entity;

import com.czertainly.api.model.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;

public class CertificateEntityDto extends NameAndUuidDto {

    @Schema(description = "Description of the Entity")
    private String description;

    @Schema(description = "Type of the Entity",
            required = true)
    private CertificateEntityCode entityType;

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
