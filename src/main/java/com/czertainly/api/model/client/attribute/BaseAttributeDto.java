package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v2.*;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Base Attribute definition",
        type = "object",
        discriminatorProperty = "type",
        discriminatorMapping = {
                @DiscriminatorMapping(value = AttributeType.Codes.DATA, schema = DataAttribute.class),
                @DiscriminatorMapping(value = AttributeType.Codes.INFO, schema = InfoAttribute.class),
                @DiscriminatorMapping(value = AttributeType.Codes.GROUP, schema = GroupAttribute.class),
                @DiscriminatorMapping(value = AttributeType.Codes.META, schema = MetadataAttribute.class),
                @DiscriminatorMapping(value = AttributeType.Codes.CUSTOM, schema = CustomAttribute.class)
        },
        oneOf = {
                DataAttribute.class,
                InfoAttribute.class,
                GroupAttribute.class,
                MetadataAttribute.class,
                CustomAttribute.class
        }
)
public interface BaseAttributeDto {
        /**
         * Version of the Attribute
         **/
        @Schema(
                description = "Version of the Attribute",
                example = "2",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                defaultValue = "2"
        )
        int getVersion();

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