package com.czertainly.api.model.client.attribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * This class contains set of properties to represent
 * an Attribute definition including its value for the
 * detail API responses
 */
@Setter
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "version", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ResponseAttributeV3Dto.class, name = "3"),
        @JsonSubTypes.Type(value = ResponseAttributeV2Dto.class, name = "2")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(implementation = ResponseAttributeDto.class)
public abstract class ResponseAttribute implements ResponseAttributeDto {

    public abstract <T> T getContent();

}
