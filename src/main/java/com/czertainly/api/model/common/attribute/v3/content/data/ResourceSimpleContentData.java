package com.czertainly.api.model.common.attribute.v3.content.data;

import com.czertainly.api.model.client.attribute.ResponseAttribute;
import com.czertainly.api.model.core.auth.AttributeResource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(
        title = "ResourceSimpleContentData",
        description = "Content data for resource object attribute containing secret content"
)
public class ResourceSimpleContentData extends ResourceObjectContentData {

    @Schema(description = "Attributes of the resource object", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<ResponseAttribute> attributes;

    public ResourceSimpleContentData(AttributeResource resource) {
        super(resource);
    }

    public ResourceSimpleContentData(AttributeResource resource, String uuid, String name, List<ResponseAttribute> attributes) {
        this(resource);
        this.uuid = uuid;
        this.name = name;
        this.attributes = attributes;
    }

}
