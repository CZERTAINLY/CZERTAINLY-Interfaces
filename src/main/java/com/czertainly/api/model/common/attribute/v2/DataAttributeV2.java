package com.czertainly.api.model.common.attribute.v2;

import com.czertainly.api.model.common.attribute.common.AttributeType;
import com.czertainly.api.model.common.attribute.common.AttributeVersion;
import com.czertainly.api.model.common.attribute.common.DataAttribute;
import com.czertainly.api.model.common.attribute.common.callback.AttributeCallback;
import com.czertainly.api.model.common.attribute.common.constraint.BaseAttributeConstraint;
import com.czertainly.api.model.common.attribute.common.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v2.content.*;
import com.czertainly.api.model.common.attribute.common.properties.DataAttributeProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
        description = "Data attribute allows to store and transfer dynamic data. Its content can be edited and send in requests to store.",
        type = "object"
)
@JsonDeserialize
public class DataAttributeV2 extends BaseAttributeV2<List<BaseAttributeContentV2>> implements DataAttribute<BaseAttributeContentV2> {

    /**
     * Content of the Attribute
     **/
    @ArraySchema(
            schema = @Schema(
                    ref = "BaseAttributeContentDtoV2"
            ),
            arraySchema = @Schema(
                    description = "Content of the Attribute"
            )
    )
    private List<BaseAttributeContentV2> content;

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
            description = "Optional constraints used for validating the Attribute content"
    )
    private List<BaseAttributeConstraint> constraints;

    /**
     * Optional definition of callback for getting the content of the Attribute based on the action
     **/
    @Schema(
            description = "Optional definition of callback for getting the content of the Attribute based on the action"
    )
    private AttributeCallback attributeCallback;

    @Schema
    private AttributeVersion schemaVersion = AttributeVersion.V2;

    public DataAttributeV2() {
        super(AttributeType.DATA);
    }

    public DataAttributeV2(String type) {
        super(AttributeType.fromCode(type));
    }

    public DataAttributeV2(DataAttributeV2 original) {
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

    @Override
    public void setContent(List<BaseAttributeContentV2> content) {
        this.content = content;
    }
}
