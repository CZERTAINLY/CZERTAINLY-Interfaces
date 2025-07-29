package com.czertainly.api.model.core.other;

import com.czertainly.api.model.core.auth.Resource;
import com.czertainly.api.model.core.logging.Loggable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Data
@ToString
public class ResourceObjectDto implements Serializable, Loggable {

    @Schema(description = "Resource of object",
            examples = {Resource.Codes.CERTIFICATE},
            requiredMode = Schema.RequiredMode.REQUIRED)
    protected Resource resource;

    @Schema(description = "Object UUID",
            examples = {"7b55ge1c-844f-11dc-a8a3-0242ac120002"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    protected UUID objectUuid;

    @Schema(description = "Object Name",
            examples = {"Name"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    protected String name;

    public ResourceObjectDto() {
        super();
    }

    public ResourceObjectDto(Resource resource, UUID objectUuid, String name) {
        super();
        this.objectUuid = objectUuid;
        this.name = name;
    }

    @Override
    public Serializable toLogData() {
        return this;
    }
}
