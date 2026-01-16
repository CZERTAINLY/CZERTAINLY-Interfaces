package com.czertainly.api.model.common.attribute.v2;

import com.czertainly.api.model.common.attribute.v2.content.*;
import com.czertainly.api.model.common.attribute.v2.properties.InfoAttributeProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Objects;

/**
 * This class contains set of properties to represent
 * an Attribute definition including its value for the Attributes
 * of type Info.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        description = "Info attribute contains content that is for information purpose or represents additional information for object (metadata). Its content can not be edited and is not send in requests to store.",
        type = "object"
)
public class InfoAttribute extends BaseAttribute<List<BaseAttributeContent>> {

    /**
     * Content of the Attribute
     **/
    @Schema(
            description = "Content of the Attribute",
            requiredMode = Schema.RequiredMode.REQUIRED
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
    private InfoAttributeProperties properties;

    public InfoAttribute() {
        super(AttributeType.INFO);
    }

    public InfoAttribute(AttributeType type) {
        super(type);
    }

    public InfoAttribute(String type) {
        super(AttributeType.fromCode(type));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InfoAttribute that)) return false;

        return  Objects.equals(getUuid(), that.getUuid()) && Objects.equals(getName(), that.getName())
                && Objects.equals(getDescription(), that.getDescription())
                && Objects.equals(content, that.content)
                && contentType == that.contentType
                && Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                content,
                contentType,
                properties
        );
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("content", content)
                .append("contentType", contentType)
                .append("properties", properties).toString();
    }
}
