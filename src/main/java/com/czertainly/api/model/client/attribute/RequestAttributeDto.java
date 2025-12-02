package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.common.AttributeContent;
import com.czertainly.api.model.common.attribute.v2.content.*;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * This class contains set of properties to represent
 * an Attribute definition provided by the client
 */

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
public interface RequestAttributeDto {


    /**
     * UUID of the Attribute
     **/
    @Schema(
            description = "UUID of the Attribute",
            example = "b11c9be1-b619-4ef5-be1b-a1cd9ef265b7",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    String getUuid();

    /**
     * Name of the Attribute
     **/
    @Schema(
            description = "Name of the Attribute",
            examples = {"Attribute"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
   String getName();

    /**
     * Content Type of the Attribute
     **/
    @Schema(
            description = "Content Type of the Attribute",
            examples = {"Attribute"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    AttributeContentType getContentType();


    @Schema(
            description = "Version of the Attribute",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    int getVersion();


}
