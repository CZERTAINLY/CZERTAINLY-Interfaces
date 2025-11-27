package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContentV2;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class RequestAttributeV2Dto extends RequestAttributeDto<BaseAttributeContentV2<?>> {


    @Override
    public int getVersion() {
        return 2;
    }
}
