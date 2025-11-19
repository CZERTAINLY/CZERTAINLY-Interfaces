package com.czertainly.api.model.common.attribute.common;

import com.czertainly.api.model.common.attribute.v2.content.AttributeContent;
import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContentV2;
import com.czertainly.api.model.common.attribute.v3.BaseAttributeV3;
import com.czertainly.api.model.common.attribute.v3.content.BaseAttributeContentV3;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "version", defaultImpl = BaseAttributeV3.class, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BaseAttributeContentV3.class, name = "3"),
        @JsonSubTypes.Type(value = BaseAttributeContentV2.class, name = "2")
})
@Getter
@Setter
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(implementation = BaseAttributeContentDto.class)
public abstract class BaseAttributeContent extends AttributeContent implements BaseAttributeContentDto {

    @Schema
    private int version;

}
