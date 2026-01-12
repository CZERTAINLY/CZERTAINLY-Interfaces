package com.czertainly.api.model.common.attribute.v3.content.data;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.attribute.common.BaseAttribute;
import com.czertainly.api.model.core.auth.AttributeResource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceObjectContentData extends NameAndUuidDto {

    @Schema(description = "Resource contained in data", requiredMode = Schema.RequiredMode.REQUIRED)
    private AttributeResource resource;

    @Schema(description = "Content of the resource object", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String base64Content;

    @Schema(description = "Attributes of the resource object", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<BaseAttribute> attributes;
}
