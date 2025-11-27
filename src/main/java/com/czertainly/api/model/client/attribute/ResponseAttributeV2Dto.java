package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContentV2;

public class ResponseAttributeV2Dto extends ResponseAttributeDto<BaseAttributeContentV2<?>> {

    @Override
    public int getVersion() {
        return 2;
    }
}
