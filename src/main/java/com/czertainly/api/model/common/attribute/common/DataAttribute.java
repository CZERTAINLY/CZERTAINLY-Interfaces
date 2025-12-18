package com.czertainly.api.model.common.attribute.common;

import com.czertainly.api.model.common.attribute.v2.DataAttributeV2;
import com.czertainly.api.model.common.attribute.common.callback.AttributeCallback;
import com.czertainly.api.model.common.attribute.common.constraint.BaseAttributeConstraint;
import com.czertainly.api.model.common.attribute.common.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.common.properties.DataAttributeProperties;
import com.czertainly.api.model.common.attribute.v3.DataAttributeV3;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(
        description = "Data Attribute",
        type = "object",
        discriminatorProperty = "schemaVersion",
        oneOf = {
                DataAttributeV2.class,
                DataAttributeV3.class
        }

)
public interface DataAttribute<T> extends Attribute {

    List<T> getContent();
    void setContent(List<T> content);
    AttributeContentType getContentType();

    DataAttributeProperties getProperties();

    List<BaseAttributeConstraint> getConstraints();

    AttributeCallback getAttributeCallback();



}
