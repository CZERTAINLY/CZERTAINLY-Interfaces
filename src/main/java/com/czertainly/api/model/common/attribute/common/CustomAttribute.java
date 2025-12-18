package com.czertainly.api.model.common.attribute.common;

import com.czertainly.api.model.common.attribute.common.constraint.BaseAttributeConstraint;
import com.czertainly.api.model.common.attribute.common.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.common.properties.CustomAttributeProperties;
import com.czertainly.api.model.common.attribute.common.properties.DataAttributeProperties;
import com.czertainly.api.model.common.attribute.v2.CustomAttributeV2;
import com.czertainly.api.model.common.attribute.v3.CustomAttributeV3;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(
        description = "Custom Attribute",
        type = "object",
        oneOf = {
                CustomAttributeV2.class,
                CustomAttributeV3.class
        }
)
public interface CustomAttribute<T> extends Attribute {

    List<T> getContent();
    void setContent(List<T> content);
    AttributeContentType getContentType();

    CustomAttributeProperties getProperties();
}
