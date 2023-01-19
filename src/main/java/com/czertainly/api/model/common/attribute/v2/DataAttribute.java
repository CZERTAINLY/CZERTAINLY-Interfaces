package com.czertainly.api.model.common.attribute.v2;

import com.czertainly.api.model.common.attribute.v2.callback.AttributeCallback;
import com.czertainly.api.model.common.attribute.v2.constraint.BaseAttributeConstraint;
import com.czertainly.api.model.common.attribute.v2.constraint.DateTimeAttributeConstraint;
import com.czertainly.api.model.common.attribute.v2.constraint.RangeAttributeConstraint;
import com.czertainly.api.model.common.attribute.v2.constraint.RegexpAttributeConstraint;
import com.czertainly.api.model.common.attribute.v2.content.*;
import com.czertainly.api.model.common.attribute.v2.properties.DataAttributeProperties;
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
@Schema(description = "Data attribute allows to store and transfer dynamic data. Its content can be edited and send in requests to store.")
public class DataAttribute extends BaseAttribute<List<BaseAttributeContent>> {

    /**
     * Content of the Attribute
     **/
    @Schema(
        description = "Content of the Attribute",
        type = "object",
        discriminatorProperty = "contentType",
        oneOf = {
            BooleanAttributeContent.class,
            CredentialAttributeContent.class,
            DateAttributeContent.class,
            DateTimeAttributeContent.class,
            FileAttributeContent.class,
            FloatAttributeContent.class,
            IntegerAttributeContent.class,
            ObjectAttributeContent.class,
            SecretAttributeContent.class,
            StringAttributeContent.class,
            TextAttributeContent.class,
            TimeAttributeContent.class
        }
    )
    private List<BaseAttributeContent> content;

    /**
     * Type of the Attribute content
     */
    @Schema(
            description = "Type of the Content",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private AttributeContentType contentType;


    /**
     * Properties of the Attributes
     */
    @Schema(
            description = "Properties of the Attributes",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private DataAttributeProperties properties;

    /**
     * List of constraints for the Attributes
     **/
    @Schema(
        description = "Optional regular expressions and constraints used for validating the Attribute content",
        type = "object",
        oneOf = {
            RegexpAttributeConstraint.class,
            RangeAttributeConstraint.class,
            DateTimeAttributeConstraint.class
        }
    )
    private List<BaseAttributeConstraint> constraints;

    /**
     * Optional definition of callback for getting the content of the Attribute based on the action
     **/
    @Schema(
            description = "Optional definition of callback for getting the content of the Attribute based on the action"
    )
    private AttributeCallback attributeCallback;

    public DataAttribute() {
        super(AttributeType.DATA);
    }

    public DataAttribute(String type) {
        super(AttributeType.fromCode(type));
    }

    public DataAttribute(DataAttribute original) {
        super(AttributeType.DATA);
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

    public DataAttributeProperties getProperties() {
        return properties;
    }

    public void setProperties(DataAttributeProperties properties) {
        this.properties = properties;
    }

    public List<BaseAttributeConstraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<BaseAttributeConstraint> constraints) {
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
