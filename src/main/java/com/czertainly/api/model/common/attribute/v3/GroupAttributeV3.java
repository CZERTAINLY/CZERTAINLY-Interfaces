package com.czertainly.api.model.common.attribute.v3;

import com.czertainly.api.model.common.attribute.v2.AttributeType;
import com.czertainly.api.model.common.attribute.v2.callback.AttributeCallback;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * This class contains set of properties to represent
 * an Attribute definition including its value for the Attributes
 * of type Group.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        description = "Group attribute and its content represents dynamic list of additional attributes retrieved by callback. Its content can not be edited and is not send in requests to store.",
        type = "object"
)
public class GroupAttributeV3 extends BaseAttributeV3<List<BaseAttributeV3>> {

    /**
     * Content of the Attribute
     **/
    @ArraySchema(
            schema = @Schema(
                    ref = "BaseAttributeDto"
            ),
            arraySchema = @Schema(
                    description = "List of all different types of attributes"
            )
    )
    private List<BaseAttributeV3> content;

    /**
     * Optional definition of callback for getting the content of the Attribute based on the action
     **/
    @Schema(
            description = "Optional definition of callback for getting the content of the Attribute based on the action"
    )
    private AttributeCallback attributeCallback;

    public GroupAttributeV3() {
        super(AttributeType.GROUP);
    }

    public GroupAttributeV3(String type) {
        super(AttributeType.fromCode(type));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("content", content)
                .append("attributeCallback", attributeCallback)
                .toString();
    }
}
