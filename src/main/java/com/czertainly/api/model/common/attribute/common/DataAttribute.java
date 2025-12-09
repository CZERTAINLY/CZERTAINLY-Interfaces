package com.czertainly.api.model.common.attribute.common;

import com.czertainly.api.model.common.attribute.v2.BaseAttributeV2;
import com.czertainly.api.model.common.attribute.v2.DataAttributeV2;
import com.czertainly.api.model.common.attribute.v2.callback.AttributeCallback;
import com.czertainly.api.model.common.attribute.v2.constraint.BaseAttributeConstraint;
import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v2.properties.DataAttributeProperties;
import com.czertainly.api.model.common.attribute.v3.BaseAttributeV3;
import com.czertainly.api.model.common.attribute.v3.DataAttributeV3;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(
        description = "Data Attribute",
        type = "object",
        discriminatorProperty = "schemaVersion",
        oneOf = {
                DataAttributeV2.class,
                DataAttributeV3.class
        },
        discriminatorMapping = {
                @DiscriminatorMapping(value = AttributeVersion.Codes.V2, schema = DataAttributeV2.class),
                @DiscriminatorMapping(value = AttributeVersion.Codes.V3, schema = DataAttributeV3.class)
        }

)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "schemaVersion", defaultImpl = DataAttributeV2.class, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DataAttributeV3.class, name = AttributeVersion.Codes.V3),
        @JsonSubTypes.Type(value = DataAttributeV2.class, name = AttributeVersion.Codes.V2)
})
public interface DataAttribute<T> extends Attribute {

    List<T> getContent();
    void setContent(List<T> content);
    AttributeContentType getContentType();

    DataAttributeProperties getProperties();

    List<BaseAttributeConstraint> getConstraints();

    AttributeCallback getAttributeCallback();



}
