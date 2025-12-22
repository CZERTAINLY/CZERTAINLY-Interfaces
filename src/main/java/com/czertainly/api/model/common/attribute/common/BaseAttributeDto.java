package com.czertainly.api.model.common.attribute.common;

import com.czertainly.api.model.common.attribute.v2.BaseAttributeV2;
import com.czertainly.api.model.common.attribute.v3.BaseAttributeV3;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(
        description = "Base Attribute definition",
        type = "object",
        oneOf = {
                BaseAttributeV2.class,
                BaseAttributeV3.class
        }
)
public interface BaseAttributeDto extends Serializable {

}
