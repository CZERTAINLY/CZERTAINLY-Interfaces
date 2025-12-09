package com.czertainly.api.model.client.attribute;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "version", visible = true, defaultImpl = RequestAttributeV2.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RequestAttributeV3.class, name = "3"),
        @JsonSubTypes.Type(value = RequestAttributeV2.class, name = "2")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(implementation = RequestAttributeDto.class)
public abstract class RequestAttribute implements RequestAttributeDto {

    public abstract <T> T getContent();

}
