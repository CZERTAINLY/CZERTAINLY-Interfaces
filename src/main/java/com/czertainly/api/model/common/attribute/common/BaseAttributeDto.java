package com.czertainly.api.model.common.attribute.common;

import com.czertainly.api.model.common.attribute.v2.BaseAttributeV2;
import com.czertainly.api.model.common.attribute.v3.BaseAttributeV3;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Base Attribute definition",
        type = "object",
        discriminatorProperty = "version",
        discriminatorMapping = {
                @DiscriminatorMapping(value = "2", schema = BaseAttributeV2.class),
                @DiscriminatorMapping(value = "3", schema = BaseAttributeV3.class),
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
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        int getVersion();
}
