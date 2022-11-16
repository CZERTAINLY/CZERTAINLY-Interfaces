package com.czertainly.api.model.common.attribute.v2;

import com.czertainly.api.model.common.attribute.v2.callback.AttributeCallback;
import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
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
            description = "List of all different types of attributes",
            required = true,
            type = "object",
            oneOf = {DataAttribute.class, InfoAttribute.class, GroupAttribute.class}
    )
    private List<BaseAttribute> content;

    /**
     * Optional definition of callback for getting the content of the Attribute based on the action
     **/
    @Schema(
            description = "Optional definition of callback for getting the content of the Attribute based on the action"
    )
    private AttributeCallback attributeCallback;

    public GroupAttribute() {
        super(AttributeType.GROUP);
    }

    public List<BaseAttribute> getContent() {
        return content;
    }

    public void setContent(List<BaseAttribute> content) {
        this.content = content;
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
                .append("attributeCallback", attributeCallback)
                .toString();
    }
}
