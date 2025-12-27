package com.czertainly.api.model.common.attribute.common;

import com.czertainly.api.model.common.attribute.v2.DataAttributeV2;
import com.czertainly.api.model.common.attribute.v3.DataAttributeV3;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Data Attribute",
        type = "object",
        oneOf = {
                DataAttributeV2.class,
                DataAttributeV3.class
        },
        discriminatorProperty = "schemaVersion",
        discriminatorMapping = {
                @DiscriminatorMapping(value = AttributeVersion.Codes.V2, schema = DataAttributeV2.class),
                @DiscriminatorMapping(value = AttributeVersion.Codes.V3, schema = DataAttributeV3.class)
        }

)
public interface DataAttributeDto {

    AttributeVersion getSchemaVersion();
}
