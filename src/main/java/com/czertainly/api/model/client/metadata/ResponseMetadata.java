package com.czertainly.api.model.client.metadata;

import com.czertainly.api.model.common.attribute.v3.BaseAttributeV3;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "version", defaultImpl = BaseAttributeV3.class, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ResponseMetadataV3.class, name = "3"),
        @JsonSubTypes.Type(value = ResponseMetadataV2.class, name = "2")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(implementation = ResponseMetadataDto.class)
public abstract class ResponseMetadata implements ResponseMetadataDto {

    public abstract <T> T getContent();

}
