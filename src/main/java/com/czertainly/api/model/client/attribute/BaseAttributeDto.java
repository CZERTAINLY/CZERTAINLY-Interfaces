package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v2.*;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Setter
@Getter
@Schema(
        description = "Base Attribute definition",
        type = "object",
        discriminatorProperty = "type",
        discriminatorMapping = {
                @DiscriminatorMapping(value = "data", schema = DataAttribute.class),
                @DiscriminatorMapping(value = "info", schema = InfoAttribute.class),
                @DiscriminatorMapping(value = "group", schema = GroupAttribute.class),
                @DiscriminatorMapping(value = "meta", schema = MetadataAttribute.class),
                @DiscriminatorMapping(value = "custom", schema = CustomAttribute.class)
        },
        oneOf = {
                DataAttribute.class,
                InfoAttribute.class,
                GroupAttribute.class,
                MetadataAttribute.class,
                CustomAttribute.class
        }
)
public class BaseAttributeDto {
        /**
         * Version of the Attribute
         **/
        @Schema(
                description = "Version of the Attribute",
                example = "2",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                defaultValue = "2"
        )
        private int version = 2;

        /**
         * UUID of the Attribute
         **/
        @Schema(
                description = "UUID of the Attribute for unique identification",
                example = "166b5cf52-63f2-11ec-90d6-0242ac120003",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        private String uuid;

        /**
         * Name of the Attribute for processing
         **/
        @Schema(
                description = "Name of the Attribute that is used for identification",
                example = "Attribute",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        private String name;

        /**
         * Optional description of the Attribute, should contain helper text on what is expected
         **/
        @Schema(
                description = "Optional description of the Attribute, should contain helper text on what is expected"
        )
        private String description;

        /**
         * Type of the Attribute
         */
        @Schema(
                description = "Type of the Attribute",
                requiredMode = Schema.RequiredMode.REQUIRED,
                defaultValue = "data"
        )
        private AttributeType type = AttributeType.DATA;

        @Schema(
                description = "Content of the Attribute",
                type = "object"
        )
        private Object content;

        public BaseAttributeDto() {
        }

        public BaseAttributeDto(AttributeType type) {
                this.type = type;
        }

    @Override
        public String toString() {
                return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                        .append("uuid", uuid)
                        .append("name", name)
                        .append("description", description)
                        .append("type", type)
                        .append("content", content)
                        .toString();
        }
}