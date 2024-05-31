package com.czertainly.api.model.common.attribute.v2;

import com.czertainly.api.model.common.attribute.v2.content.*;
import com.czertainly.api.model.common.attribute.v2.properties.CustomAttributeProperties;
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
@Schema(description = "Custom attribute allows to store and transfer dynamic data. Its content can be edited and send in requests to store.")
public class CustomAttribute extends BaseAttribute<List<BaseAttributeContent>> {

    /**
     * Content of the Attribute
     **/
    @Schema(
            description = "Content of the Attribute",
            type = "object",
            discriminatorProperty = "contentType",
            oneOf = {
                    BooleanAttributeContent.class,
                    CodeBlockAttributeContent.class,
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
    private CustomAttributeProperties properties;

    public CustomAttribute() {
        super(AttributeType.CUSTOM);
    }

    public CustomAttribute(String type) {
        super(AttributeType.fromCode(type));
    }

    public CustomAttribute(CustomAttribute original) {
        super(AttributeType.CUSTOM);
        setUuid(original.getUuid());
        setName(original.getName());
        this.content = original.content;
        this.properties = original.properties;
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

    public CustomAttributeProperties getProperties() {
        return properties;
    }

    public void setProperties(CustomAttributeProperties properties) {
        this.properties = properties;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("content", content)
                .append("contentType", contentType)
                .append("properties", properties)
                .toString();
    }
}
