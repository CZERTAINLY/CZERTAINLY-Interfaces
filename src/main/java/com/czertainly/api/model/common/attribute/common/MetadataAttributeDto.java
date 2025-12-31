package com.czertainly.api.model.common.attribute.common;

import com.czertainly.api.model.common.attribute.v2.MetadataAttributeV2;
import com.czertainly.api.model.common.attribute.v3.MetadataAttributeV3;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Base Attribute definition",
        type = "object",
        oneOf = {
                MetadataAttributeV2.class,
                MetadataAttributeV3.class
        }
)
public interface MetadataAttributeDto {


}
