package com.czertainly.api.model.common.attribute;

import com.czertainly.api.model.common.attribute.callback.AttributeCallback;
import com.czertainly.api.model.common.attribute.constraint.AttributeConstraint;
import com.czertainly.api.model.common.attribute.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.content.BaseAttributeContent;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * This class contains set of properties to represent
 * an Attribute definition including its value for the attributes
 * of type Data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataAttribute extends BaseAttribute<List<BaseAttributeContent>> {

    /**
     * Content of the Attribute
     **/
    @Schema(
            description = "Content of the Attribute"
    )
    private List<BaseAttributeContent> content;

    /**
     * Type of the Attribute content
     */
    @Schema(
            description = "Type of the Content",
            required = true
    )
    private AttributeContentType contentType;


    /**
     * Properties of the Attributes
     */
    @Schema(
            description = "Properties of the Attributes",
            required = true
    )
    private AttributeProperties properties;

    /**
     * List of constraints for the Attributes
     **/
    @Schema(
            description = "Optional regular expressions and constraints used for validating the Attribute content"
    )
    private List<AttributeConstraint> constraints;

    /**
     * Optional definition of callback for getting the content of the Attribute based on the action
     **/
    @Schema(
            description = "Optional definition of callback for getting the content of the Attribute based on the action"
    )
    private AttributeCallback attributeCallback;

    public DataAttribute() {
        super();
    }

    public DataAttribute(DataAttribute original) {
        super();
        setUuid(original.getUuid());
        setName(original.getName());
        this.content = original.content;
        this.properties = original.properties;
        this.constraints = original.constraints;
        this.attributeCallback = original.attributeCallback;
        this.contentType = original.contentType;
        setDescription(original.getDescription());
        setType(original.getType());
        setContentType(original.contentType);
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

    public AttributeProperties getProperties() {
        return properties;
    }

    public void setProperties(AttributeProperties properties) {
        this.properties = properties;
    }

    public List<AttributeConstraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<AttributeConstraint> constraints) {
        this.constraints = constraints;
    }

    public AttributeCallback getAttributeCallback() {
        return attributeCallback;
    }

    public void setAttributeCallback(AttributeCallback attributeCallback) {
        this.attributeCallback = attributeCallback;
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("content", content)
                .append("contentType", contentType)
                .append("properties", properties)
                .append("constraints", constraints)
                .append("attributeCallback", attributeCallback)
                .toString();
    }
}
