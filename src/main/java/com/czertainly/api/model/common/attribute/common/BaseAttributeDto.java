package com.czertainly.api.model.common.attribute.common;

import com.czertainly.api.model.common.attribute.v2.BaseAttributeV2;
import com.czertainly.api.model.common.attribute.v3.BaseAttributeV3;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        title = "BaseAttributeDto",
        description = "Base Attribute definition",
        type = "object",
        discriminatorProperty = "schemaVersion",
        discriminatorMapping = {
                @DiscriminatorMapping(value = AttributeVersion.Codes.V2, schema = BaseAttributeV2.class),
                @DiscriminatorMapping(value = AttributeVersion.Codes.V3, schema = BaseAttributeV3.class),
        },
        oneOf = {
                BaseAttributeV2.class,
                BaseAttributeV3.class
        }
)
public interface BaseAttributeDto {
        /**
         * Version of the Attribute
         **/
        @Schema(
                description = "Version of the Attribute",
                example = "3",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        AttributeVersion getSchemaVersion();
}
