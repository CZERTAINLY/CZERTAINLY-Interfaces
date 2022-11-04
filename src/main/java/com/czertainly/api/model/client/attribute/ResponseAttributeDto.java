package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v2.AttributeType;
import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContent;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * This class contains set of properties to represent
 * an Attribute definition including its value for the
 * detail API responses
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseAttributeDto {

    /**
     * UUID of the Attribute
     **/
    @Schema(
            description = "UUID of the Attribute",
            example = "166b5cf52-63f2-11ec-90d6-0242ac120003"
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String uuid;

    /**
     * Name of the Attribute, can be used as key for form field label text
     **/
    @Schema(
            description = "Name of the Attribute",
            example = "Attribute",
            required = true
    )
    private String name;

    /**
     * Label of the Attribute, Can be used to display the field name in the User Interface
     **/
    @Schema(
            description = "Label of the the Attribute",
            example = "Attribute Name",
            required = true
    )
    private String label;

    /**
     * Type of the Attribute, base types are defined in {@link AttributeType}
     **/
    @Schema(
            description = "Type of the Attribute",
            required = true
    )
    private AttributeType type;

    /**
     * Content Type of the Attribute
     **/
    @Schema(
            description = "Content Type of the Attribute",
            example = "Attribute",
            required = true
    )
    private AttributeContentType contentType;

    /**
     * Content of the Attribute
     **/
    @Schema(
            description = "Content of the Attribute"
    )
    private List<BaseAttributeContent> content;

    public ResponseAttributeDto() {
        super();
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public AttributeType getType() {
        return type;
    }

    public void setType(AttributeType type) {
        this.type = type;
    }

    public List<BaseAttributeContent> getContent() {
        return content;
    }

    public void setContent(List<BaseAttributeContent> content) {
        this.content = content;
    }

    public AttributeContentType getContentType() {
        return contentType;
    }

    public void setContentType(AttributeContentType contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("label", label)
                .append("type", type)
                .append("contentType", contentType)
                .append("content", content)
                .toString();
    }
}
