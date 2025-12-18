package com.czertainly.api.model.common.attribute.v3;

import com.czertainly.api.model.common.attribute.common.AttributeType;
import com.czertainly.api.model.common.attribute.common.CustomAttribute;
import com.czertainly.api.model.common.attribute.common.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.common.properties.CustomAttributeProperties;
import com.czertainly.api.model.common.attribute.v3.content.BaseAttributeContentV3;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * This class contains set of properties to represent
 * an Attribute definition including its value for the attributes
 * of type Data.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        description = "Custom attribute allows to store and transfer dynamic data. Its content can be edited and send in requests to store.",
        type = "object"
)
@JsonDeserialize
@JsonSerialize
public class CustomAttributeV3 extends BaseAttributeV3<List<BaseAttributeContentV3>> implements CustomAttribute<BaseAttributeContentV3> {

    /**
     * Content of the Attribute
     **/
    @Schema(
            description = "Content of the Attribute"
    )
    private List<BaseAttributeContentV3> content;

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

    public CustomAttributeV3() {
        super(AttributeType.CUSTOM);
    }

    public CustomAttributeV3(String type) {
        super(AttributeType.fromCode(type));
    }

    public CustomAttributeV3(CustomAttributeV3 original) {
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("content", content)
                .append("contentType", contentType)
                .append("properties", properties)
                .toString();
    }
}
