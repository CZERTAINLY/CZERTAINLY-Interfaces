package com.czertainly.api.model.common.attribute.common;

import com.czertainly.api.model.common.attribute.v2.MetadataAttributeV2;
import com.czertainly.api.model.common.attribute.v2.content.*;
import com.czertainly.api.model.common.attribute.v2.properties.MetadataAttributeProperties;
import com.czertainly.api.model.common.attribute.v3.MetadataAttributeV3;
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
public interface MetadataAttribute<T extends BaseAttributeContent> extends Attribute {

    List<T> getContent();
    void setContent(List<T> content);

    AttributeContentType getContentType();

    MetadataAttributeProperties getProperties();
}
