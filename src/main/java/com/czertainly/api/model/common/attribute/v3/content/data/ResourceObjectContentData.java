package com.czertainly.api.model.common.attribute.v3.content.data;

import com.czertainly.api.model.client.attribute.ResponseAttribute;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.core.auth.AttributeResource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceObjectContentData extends NameAndUuidDto implements Serializable {

    @Schema(description = "Resource contained in data", requiredMode = Schema.RequiredMode.REQUIRED)
    private AttributeResource resource;

    @Schema(description = "Base64 encoded content of the resource object", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String content;

    @Schema(description = "Attributes of the resource object", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<ResponseAttribute> attributes;

}
