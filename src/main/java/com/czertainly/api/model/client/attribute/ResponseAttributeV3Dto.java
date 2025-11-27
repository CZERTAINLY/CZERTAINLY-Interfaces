package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v3.content.BaseAttributeContentV3;

public class ResponseAttributeV3Dto extends ResponseAttributeDto<BaseAttributeContentV3<?>> {
    @Override
    public int getVersion() {
        return 3;
    }
}
