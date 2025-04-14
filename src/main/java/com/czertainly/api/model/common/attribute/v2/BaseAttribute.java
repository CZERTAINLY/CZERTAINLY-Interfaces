package com.czertainly.api.model.common.attribute.v2;

import com.czertainly.api.model.client.attribute.BaseAttributeDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Setter
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", defaultImpl = DataAttribute.class, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DataAttribute.class, name = AttributeType.Codes.DATA),
        @JsonSubTypes.Type(value = GroupAttribute.class, name = AttributeType.Codes.GROUP),
        @JsonSubTypes.Type(value = InfoAttribute.class, name = AttributeType.Codes.INFO),
        @JsonSubTypes.Type(value = MetadataAttribute.class, name = AttributeType.Codes.META),
        @JsonSubTypes.Type(value = CustomAttribute.class, name = AttributeType.Codes.CUSTOM)
})
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(implementation = BaseAttributeDto.class)
public class BaseAttribute<T> extends AbstractBaseAttribute implements BaseAttributeDto {

    private int version = 2;

    private String uuid;

    private String name;

    private String description;

    @Hidden
    @Schema(
        description = "Content of the Attribute"
    )
    private T content;

    private AttributeType type = AttributeType.DATA;

    public BaseAttribute() {
    }

    public BaseAttribute(AttributeType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("description", description)
                .append("content", content)
                .append("type", type)
                .toString();
    }
}
