package com.czertainly.api.model.common.attribute.v2;

import com.czertainly.api.model.common.attribute.v2.content.*;
import com.czertainly.api.model.common.attribute.v2.properties.MetadataAttributeProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Info attribute contains content that is for metadata. Its content can not be edited and is not send in requests to store.")
public class MetadataAttribute extends BaseAttribute<List<BaseAttributeContent>> {

    /**
     * Content of the Attribute
     **/
    @Schema(
            description = "Content of the Attribute",
            type = "object",
            requiredMode = Schema.RequiredMode.REQUIRED,
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
    private MetadataAttributeProperties properties;

    public MetadataAttribute() {
        super(AttributeType.META);
    }

    public MetadataAttribute(AttributeType type) {
        super(type);
    }

    public MetadataAttribute(String type) {
        super(AttributeType.fromCode(type));
    }

    @Override
    public List<BaseAttributeContent> getContent() {
        return content;
    }

    @Override
    public void setContent(List<BaseAttributeContent> content) {
        this.content = content;
    }

    public AttributeContentType getContentType() {
        return contentType;
    }

    public void setContentType(AttributeContentType contentType) {
        this.contentType = contentType;
    }

    public MetadataAttributeProperties getProperties() {
        return properties;
    }

    public void setProperties(MetadataAttributeProperties properties) {
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
