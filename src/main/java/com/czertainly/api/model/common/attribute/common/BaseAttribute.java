package com.czertainly.api.model.common.attribute.common;


import com.czertainly.api.model.common.attribute.v2.AbstractBaseAttribute;
import com.czertainly.api.model.common.attribute.v2.BaseAttributeV2;
import com.czertainly.api.model.common.attribute.v3.BaseAttributeV3;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "schemaVersion", defaultImpl = BaseAttributeV3.class, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BaseAttributeV2.class, name = AttributeVersion.Codes.V2),
        @JsonSubTypes.Type(value = BaseAttributeV3.class, name = AttributeVersion.Codes.V3)
})
@Getter
@Setter
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(implementation = BaseAttributeDto.class)
public abstract class BaseAttribute extends AbstractBaseAttribute implements BaseAttributeDto {

    @Schema
    private AttributeVersion schemaVersion;

}