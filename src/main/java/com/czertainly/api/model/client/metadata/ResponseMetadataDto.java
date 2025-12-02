package com.czertainly.api.model.client.metadata;

import com.czertainly.api.config.serializer.ResponseAttributeSerializer;
import com.czertainly.api.model.client.attribute.ResponseAttribute;
import com.czertainly.api.model.client.attribute.ResponseAttributeV2Dto;
import com.czertainly.api.model.client.attribute.ResponseAttributeV3Dto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.attribute.common.AttributeContent;
import com.czertainly.api.model.common.attribute.v2.AttributeType;
import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContentV2;
import com.czertainly.api.model.common.attribute.v3.BaseAttributeV3;
import com.czertainly.api.model.common.attribute.v3.content.BaseAttributeContentV3;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "version", defaultImpl = BaseAttributeV3.class, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ResponseMetadataV3Dto.class, name = "3"),
        @JsonSubTypes.Type(value = ResponseMetadataV2Dto.class, name = "2")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Request attribute to send attribute content for object",
        type = "object",
        discriminatorProperty = "version",
        discriminatorMapping = {
                @DiscriminatorMapping(value = "2", schema = ResponseMetadataV2Dto.class),
                @DiscriminatorMapping(value = "3", schema = ResponseMetadataV3Dto.class),
        },
        oneOf = {
               ResponseMetadataV3Dto.class,
               ResponseMetadataV2Dto.class
        })
public abstract class ResponseMetadataDto<T extends AttributeContent> {

    @Schema(description = "Source Objects", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<NameAndUuidDto> sourceObjects = new ArrayList<>();

    /**
     * UUID of the Attribute
     **/
    @Schema(
            description = "UUID of the Attribute",
            example = "b11c9be1-b619-4ef5-be1b-a1cd9ef265b7"
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String uuid;

    /**
     * Name of the Attribute, can be used as key for form field label text
     **/
    @Schema(
            description = "Name of the Attribute",
            examples = {"Attribute"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    /**
     * Label of the Attribute, Can be used to display the field name in the User Interface
     **/
    @Schema(
            description = "Label of the the Attribute",
            examples = {"Attribute Name"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String label;

    /**
     * Type of the Attribute, base types are defined in {@link AttributeType}
     **/
    @Schema(
            description = "Type of the Attribute",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private AttributeType type;

    /**
     * Content Type of the Attribute
     **/
    @Schema(
            description = "Content Type of the Attribute",
            examples = {"Attribute"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private AttributeContentType contentType;

    /**
     * Content of the Attribute
     **/
    @Schema(
            description = "Content of the Attribute"
    )
    @JsonSerialize(using = ResponseAttributeSerializer.class)
    private List<T> content;

    @Schema(
            description = "Version of the Attribute",
            examples = {"Attribute"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int version;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("sourceObjectUuids", sourceObjects)
                .toString();
    }

    public ResponseAttribute toResponseAttribute() {
        if (version == 2) {
            ResponseAttributeV2Dto responseAttributeV2Dto = new ResponseAttributeV2Dto();
            responseAttributeV2Dto.setContent((List<BaseAttributeContentV2<?>>) content);
            responseAttributeV2Dto.setUuid(uuid);
            responseAttributeV2Dto.setType(type);
            responseAttributeV2Dto.setName(name);
            responseAttributeV2Dto.setLabel(label);
            responseAttributeV2Dto.setContentType(contentType);
            return responseAttributeV2Dto;
        }
        if (version == 3) {
            ResponseAttributeV3Dto responseAttributeV3Dto = new ResponseAttributeV3Dto();
            responseAttributeV3Dto.setContent((List<BaseAttributeContentV3<?>>) content);
            responseAttributeV3Dto.setUuid(uuid);
            responseAttributeV3Dto.setType(type);
            responseAttributeV3Dto.setName(name);
            responseAttributeV3Dto.setLabel(label);
            responseAttributeV3Dto.setContentType(contentType);
            return responseAttributeV3Dto;
        }
        return null;
    }
}
