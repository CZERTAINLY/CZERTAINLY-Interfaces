package com.czertainly.api.model.common.attribute.common;

import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContentV2;
import com.czertainly.api.model.common.attribute.v3.content.BaseAttributeContentV3;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Base Attribute definition",
        type = "object",
        discriminatorProperty = "version",
        discriminatorMapping = {
                @DiscriminatorMapping(value = "2", schema = BaseAttributeContentV2.class),
                @DiscriminatorMapping(value = "3", schema = BaseAttributeContentV3.class),
        },
        oneOf = {BaseAttributeContentV2.class,
                BaseAttributeContentV3.class
        }
)
public interface BaseAttributeContentDto {

    /**
     * Version of the Attribute
     **/
    @Schema(
            description = "Version of the Attribute",
            example = "3",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    int getVersion();
}
