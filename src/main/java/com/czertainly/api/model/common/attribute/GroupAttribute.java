package com.czertainly.api.model.common.attribute;

import com.czertainly.api.model.common.attribute.callback.AttributeCallback;
import com.czertainly.api.model.common.attribute.content.AttributeContentType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * This class contains set of properties to represent
 * an Attribute definition including its value for the Attributes
 * of type Group.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupAttribute extends BaseAttribute<List<BaseAttribute>> {

    /**
     * Content of the Attribute
     **/
    @Schema(
            description = "Content of the Attribute",
            required = true
    )
    private List<BaseAttribute> content;

    /**
     * Type of the Attribute content
     */
    @Schema(
            description = "Type of the Content",
            required = true
    )
    private AttributeContentType contentType;

    /**
     * Optional definition of callback for getting the content of the Attribute based on the action
     **/
    @Schema(
            description = "Optional definition of callback for getting the content of the Attribute based on the action"
    )
    private AttributeCallback attributeCallback;

    public GroupAttribute() {
        super();
    }

    public List<BaseAttribute> getContent() {
        return content;
    }

    public void setContent(List<BaseAttribute> content) {
        this.content = content;
    }

    public AttributeContentType getContentType() {
        return contentType;
    }

    public void setContentType(AttributeContentType contentType) {
        this.contentType = contentType;
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
                .append("attributeCallback", attributeCallback)
                .toString();
    }
}
