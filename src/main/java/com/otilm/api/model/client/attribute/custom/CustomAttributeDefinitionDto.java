package com.otilm.api.model.client.attribute.custom;

import com.otilm.api.model.client.attribute.AttributeDefinitionDto;
import com.otilm.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomAttributeDefinitionDto extends AttributeDefinitionDto {

    /**
     * Resources of the Custom Attribute
     */
    @Schema(description = "List of resources for custom attribute", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Resource> resources;

    /**
     * Boolean determining if the Attribute is required. If true, the Attribute must be provided.
     **/
    @Schema(description = "Boolean determining if the Attribute is required. If true, the Attribute must be provided.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean required;

}
