package com.czertainly.api.model.common.attribute.v2;

import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContent;

import java.util.List;

public class BaseContentAttribute extends BaseAttribute<List<BaseAttributeContent<?>>> {

    public BaseContentAttribute() {

    }

    public BaseContentAttribute(AttributeType type) {
        super(type);
    }

}
