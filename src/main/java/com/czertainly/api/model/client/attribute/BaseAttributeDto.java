package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v2.AttributeType;
import com.czertainly.api.model.common.attribute.v2.DataAttribute;
import com.czertainly.api.model.common.attribute.v2.GroupAttribute;
import com.czertainly.api.model.common.attribute.v2.InfoAttribute;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Schema(
        description = "Base Attribute definition",
        type = "object",
        discriminatorProperty = "type",
        discriminatorMapping = {
                @DiscriminatorMapping(value = "data", schema = DataAttribute.class),
                @DiscriminatorMapping(value = "info", schema = InfoAttribute.class),
                @DiscriminatorMapping(value = "group", schema = GroupAttribute.class)
//                @DiscriminatorMapping(value = "meta", schema = InfoAttribute.class),
//                @DiscriminatorMapping(value = "custom", schema = DataAttribute.class)
        },
        oneOf = {
                DataAttribute.class,
                InfoAttribute.class,
                GroupAttribute.class
        }
)
public class BaseAttributeDto {
        /**
         * UUID of the Attribute
         **/
        @Schema(
                description = "UUID of the Attribute for unique identification",
                example = "166b5cf52-63f2-11ec-90d6-0242ac120003",
                required = true
        )
        private String uuid;

        /**
         * Name of the Attribute for processing
         **/
        @Schema(
                description = "Name of the Attribute that is used for identification",
                example = "Attribute",
                required = true
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
                required = true,
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

        public String getUuid() {
                return uuid;
        }

        public void setUuid(String uuid) {
                this.uuid = uuid;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public AttributeType getType() {
                return type;
        }

        public void setType(AttributeType type) {
                this.type = type;
        }

        public Object getContent() {
                return content;
        }

        public void setContent(Object content) {
                this.content = content;
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