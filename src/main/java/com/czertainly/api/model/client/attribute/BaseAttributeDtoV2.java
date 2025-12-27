package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.common.AttributeType;
import com.czertainly.api.model.common.attribute.v2.*;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Base Attribute definition",
        type = "object",
        discriminatorProperty = "type",
        discriminatorMapping = {
                @DiscriminatorMapping(value = AttributeType.Codes.DATA, schema = DataAttributeV2.class),
                @DiscriminatorMapping(value = AttributeType.Codes.INFO, schema = InfoAttributeV2.class),
                @DiscriminatorMapping(value = AttributeType.Codes.GROUP, schema = GroupAttributeV2.class),
                @DiscriminatorMapping(value = AttributeType.Codes.META, schema = MetadataAttributeV2.class),
                @DiscriminatorMapping(value = AttributeType.Codes.CUSTOM, schema = CustomAttributeV2.class)
        },
        oneOf = {
                DataAttributeV2.class,
                InfoAttributeV2.class,
                GroupAttributeV2.class,
                MetadataAttributeV2.class,
                CustomAttributeV2.class
        }
)
public interface BaseAttributeDtoV2 {


        /**
         * UUID of the Attribute
         **/
        @Schema(
                description = "UUID of the Attribute for unique identification",
                example = "b11c9be1-b619-4ef5-be1b-a1cd9ef265b7",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String getUuid();

        /**
         * Name of the Attribute for processing
         **/
        @Schema(
                description = "Name of the Attribute that is used for identification",
                examples = {"Attribute"},
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String getName();

        /**
         * Optional description of the Attribute, should contain helper text on what is expected
         **/
        @Schema(
                description = "Optional description of the Attribute, should contain helper text on what is expected"
        )
        String getDescription();

        /**
         * Type of the Attribute
         */
        @Schema(
                description = "Type of the Attribute",
                requiredMode = Schema.RequiredMode.REQUIRED,
                defaultValue = AttributeType.Codes.DATA
        )
        AttributeType getType();
}