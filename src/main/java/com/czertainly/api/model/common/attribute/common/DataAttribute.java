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
        oneOf = {
                DataAttributeV2.class,
                DataAttributeV3.class
        }

)
public abstract class DataAttribute extends BaseAttribute implements DataAttributeDto {

    public abstract void setContent(List<? extends AttributeContent> content);
    public abstract AttributeContentType getContentType();
    public abstract DataAttributeProperties getProperties();

    public abstract List<BaseAttributeConstraint<?>> getConstraints();

    public abstract AttributeCallback getAttributeCallback();

}
