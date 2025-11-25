package com.czertainly.api.model.common.attribute.common;

import com.czertainly.api.model.common.attribute.v2.callback.AttributeCallback;
import com.czertainly.api.model.common.attribute.v2.constraint.BaseAttributeConstraint;
import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v2.properties.DataAttributeProperties;

import java.util.List;


public interface DataAttribute<T extends BaseAttributeContent> extends Attribute {

    List<T> getContent();
    void setContent(List<T> content);


    AttributeContentType getContentType();

    DataAttributeProperties getProperties();

    List<BaseAttributeConstraint> getConstraints();

    AttributeCallback getAttributeCallback();



}
