package com.czertainly.api.model.core.certificate.entity;

import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;

public class EntityDto extends NameAndUuidDto {

    @Schema(description = "Description of the Entity")
    private String description;

    @Schema(description = "Type of the Entity",
            required = true)
    private EntityCode entityType;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EntityCode getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityCode entityType) {
        this.entityType = entityType;
    }
}
