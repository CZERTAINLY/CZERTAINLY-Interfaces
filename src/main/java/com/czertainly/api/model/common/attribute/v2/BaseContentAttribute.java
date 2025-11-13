package com.czertainly.api.model.common.attribute.v2;

import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContentV2;

import java.util.List;

public class BaseContentAttribute extends BaseAttributeV2<List<BaseAttributeContentV2<?>>> {

    public BaseContentAttribute() {

    }

    public BaseContentAttribute(AttributeType type) {
        super(type);
    }

}
