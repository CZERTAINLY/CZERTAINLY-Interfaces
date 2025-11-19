package com.czertainly.api.model.client.attribute;

import com.czertainly.api.config.serializer.ResponseAttributeSerializer;
import com.czertainly.api.model.common.attribute.common.BaseAttributeContent;
import com.czertainly.api.model.common.attribute.v2.AttributeType;
import com.czertainly.api.model.common.attribute.v2.content.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * This class contains set of properties to represent
 * an Attribute definition including its value for the
 * detail API responses
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Response attribute with content for object it belongs to")
@Getter
@Setter
public class ResponseAttributeDto implements Serializable {

    /**
     * UUID of the Attribute
     **/
    @Schema(
            description = "UUID of the Attribute",
            example = "b11c9be1-b619-4ef5-be1b-a1cd9ef265b7"
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String uuid;

    /**
     * Name of the Attribute, can be used as key for form field label text
     **/
    @Schema(
            description = "Name of the Attribute",
            examples = {"Attribute"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    /**
     * Label of the Attribute, Can be used to display the field name in the User Interface
     **/
    @Schema(
            description = "Label of the the Attribute",
            examples = {"Attribute Name"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String label;

    /**
     * Type of the Attribute, base types are defined in {@link AttributeType}
     **/
    @Schema(
            description = "Type of the Attribute",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private AttributeType type;

    /**
     * Content Type of the Attribute
     **/
    @Schema(
            description = "Content Type of the Attribute",
            examples = {"Attribute"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private AttributeContentType contentType;

    /**
     * Content of the Attribute
     **/
    @Schema(
            description = "Content of the Attribute"
    )
    @JsonSerialize(using = ResponseAttributeSerializer.class)
    private List<BaseAttributeContent> content;

    public ResponseAttributeDto() {
        super();
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
