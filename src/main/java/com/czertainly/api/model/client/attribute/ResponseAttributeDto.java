package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v2.AttributeType;
import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Response attribute to send attribute content for object",
        type = "object",
        discriminatorProperty = "version",
        discriminatorMapping = {
                @DiscriminatorMapping(value = "2", schema = ResponseAttributeV2Dto.class),
                @DiscriminatorMapping(value = "3", schema = ResponseAttributeV3Dto.class),
        },
        oneOf = {
                ResponseAttributeV2Dto.class,
                ResponseAttributeV3Dto.class
        }
)
public interface ResponseAttributeDto {

        /**
         * UUID of the Attribute
         **/
        @Schema(
                description = "UUID of the Attribute",
                example = "b11c9be1-b619-4ef5-be1b-a1cd9ef265b7",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String getUuid();

        /**
         * Name of the Attribute
         **/
        @Schema(
                description = "Name of the Attribute",
                examples = {"Attribute"},
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String getName();

        /**
         * Label of the Attribute, Can be used to display the field name in the User Interface
         **/
        @Schema(
                description = "Label of the the Attribute",
                examples = {"Attribute Name"},
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String getLabel();

        /**
         * Type of the Attribute, base types are defined in {@link AttributeType}
         **/
        @Schema(
                description = "Type of the Attribute",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        AttributeType getType();

        /**
         * Content Type of the Attribute
         **/
        @Schema(
                description = "Content Type of the Attribute",
                examples = {"Attribute"},
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        AttributeContentType getContentType();

        @Schema(
                description = "Version of the Attribute",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        int getVersion();
}
