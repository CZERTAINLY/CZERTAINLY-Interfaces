package com.czertainly.api.model.common.attribute.v3;

import com.czertainly.api.model.common.attribute.v2.AttributeType;
import com.czertainly.api.model.common.attribute.v3.content.BaseAttributeContentV3;

import java.util.List;

public class BaseContentAttributeV3 extends BaseAttributeV3<List<BaseAttributeContentV3<?>>> {

    public BaseContentAttributeV3() {

    }

    public BaseContentAttributeV3(AttributeType type) {
        super(type);
    }

}
