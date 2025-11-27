package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.common.AttributeContent;
import com.czertainly.api.model.common.attribute.v2.content.*;
import com.czertainly.api.model.common.attribute.v3.BaseAttributeV3;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * This class contains set of properties to represent
 * an Attribute definition provided by the client
 */
@Setter
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "version", defaultImpl = BaseAttributeV3.class, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RequestAttributeV3Dto.class, name = "3"),
        @JsonSubTypes.Type(value = RequestAttributeV2Dto.class, name = "2")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Request attribute to send attribute content for object",
        type = "object",
        discriminatorProperty = "version",
        discriminatorMapping = {
                @DiscriminatorMapping(value = "2", schema = RequestAttributeV2Dto.class),
                @DiscriminatorMapping(value = "3", schema = RequestAttributeV3Dto.class),
        },
        oneOf = {
                RequestAttributeV3Dto.class,
                RequestAttributeV2Dto.class
        })
public class RequestAttributeDto<T extends AttributeContent> implements Serializable {


    /**
     * UUID of the Attribute
     **/
    @Schema(
            description = "UUID of the Attribute",
            example = "b11c9be1-b619-4ef5-be1b-a1cd9ef265b7",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String uuid;

    /**
     * Name of the Attribute
     **/
    @Schema(
            description = "Name of the Attribute",
            examples = {"Attribute"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    /**
     * Content Type of the Attribute
     **/
    @Schema(
            description = "Content Type of the Attribute",
            examples = {"Attribute"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private AttributeContentType contentType;


    @Schema(
            description = "Version of the Attribute",
            examples = {"Attribute"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int version;

    /**
     * Content of the Attribute
     **/
    @Schema(
            description = "Content of the Attribute",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<T> content;
}
