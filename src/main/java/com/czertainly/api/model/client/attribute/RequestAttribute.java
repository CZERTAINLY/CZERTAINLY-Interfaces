package com.czertainly.api.model.client.attribute;


import com.czertainly.api.model.common.attribute.common.BaseAttributeDto;
import com.czertainly.api.model.common.attribute.v2.AttributeType;
import com.czertainly.api.model.common.attribute.v2.BaseAttributeV2;
import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v3.BaseAttributeV3;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "version", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RequestAttributeV3Dto.class, name = "3"),
        @JsonSubTypes.Type(value = RequestAttributeV2Dto.class, name = "2")
})
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(implementation = RequestAttributeDto.class)
public abstract class RequestAttribute implements RequestAttributeDto {

    public abstract <T> T getContent();
    public abstract int getVersion();
    public abstract String getUuid();
    public abstract String getName();
    public abstract AttributeContentType getContentType();
}
