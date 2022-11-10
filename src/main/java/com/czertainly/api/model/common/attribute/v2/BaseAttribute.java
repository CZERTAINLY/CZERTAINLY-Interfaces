package com.czertainly.api.model.common.attribute.v2;

import com.czertainly.api.model.common.attribute.v2.content.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", defaultImpl = DataAttribute.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DataAttribute.class, name = "data"),
        @JsonSubTypes.Type(value = GroupAttribute.class, name = "group"),
        @JsonSubTypes.Type(value = InfoAttribute.class, name = "info")
})
@JsonInclude(JsonInclude.Include.ALWAYS)
public class BaseAttribute<T> extends AbstractBaseAttribute {

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

    @ArraySchema(schema = @Schema(
            description = "Content of the Attribute",
            required = true
    ))
    private T content;

    /**
     * Type of the Attribute
     */
    @Schema(
            description = "Type of the Attribute",
            required = true,
            defaultValue = "DATA"
    )
    private AttributeType type = AttributeType.DATA;

    public BaseAttribute() {
    }

    public BaseAttribute(AttributeType type) {
        this.type = type;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public AttributeType getType() {
        return type;
    }

    public void setType(AttributeType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("description", description)
                .append("content", content)
                .append("type", type)
                .toString();
    }
}
