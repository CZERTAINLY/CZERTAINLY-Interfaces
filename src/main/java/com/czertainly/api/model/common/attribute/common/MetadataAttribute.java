package com.czertainly.api.model.common.attribute.common;

import com.czertainly.api.model.common.attribute.v2.MetadataAttributeV2;
import com.czertainly.api.model.common.attribute.v2.content.*;
import com.czertainly.api.model.common.attribute.v2.properties.MetadataAttributeProperties;
import com.czertainly.api.model.common.attribute.v3.BaseAttributeV3;
import com.czertainly.api.model.common.attribute.v3.MetadataAttributeV3;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
@Schema(
        description = "Base Attribute ContentV2 definition",
        type = "object",
        oneOf = {
                MetadataAttributeV2.class,
                MetadataAttributeV3.class
        }
)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "schemaVersion", defaultImpl = MetadataAttributeV2.class, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MetadataAttributeV3.class, name = AttributeVersion.Codes.V3),
        @JsonSubTypes.Type(value = MetadataAttributeV2.class, name = AttributeVersion.Codes.V2)
})
public interface MetadataAttribute<T extends AttributeContent> extends Attribute {

    List<T> getContent();

    void setContent(List<T> content);

    AttributeContentType getContentType();

    MetadataAttributeProperties getProperties();
}
