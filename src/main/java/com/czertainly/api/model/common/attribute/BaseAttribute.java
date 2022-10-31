package com.czertainly.api.model.common.attribute;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class BaseAttribute<T> {

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

    @Schema(description = "Content of the Attribute", required = true)
    private T content;

    /**
     * Type of the Attribute
     */
    @Schema(
            description = "Type of the Attribute",
            required = true
    )
    private AttributeType type;

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

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

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
