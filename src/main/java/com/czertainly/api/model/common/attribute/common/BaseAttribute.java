package com.czertainly.api.model.common.attribute.common;


import com.czertainly.api.model.common.attribute.v2.AbstractBaseAttribute;
import com.czertainly.api.model.common.attribute.v2.AttributeType;
import com.czertainly.api.model.common.attribute.v2.BaseAttributeV2;
import com.czertainly.api.model.common.attribute.v3.BaseAttributeV3;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "version", defaultImpl = BaseAttributeV3.class, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BaseAttributeV3.class, name = "3"),
        @JsonSubTypes.Type(value = BaseAttributeV2.class, name = "2")
})
@Getter
@Setter
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(implementation = BaseAttributeVersionDto.class)
public class BaseAttribute extends AbstractBaseAttribute implements BaseAttributeVersionDto {

    @Schema
    private int version;

    @Schema
    private String extra;

    @Override
    public <T> T getContent() {
        return null;
    }

    @Override
    public String getUuid() {
        return "";
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public AttributeType getType() {
        return null;
    }
}