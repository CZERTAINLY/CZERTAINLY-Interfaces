package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v3.content.BaseAttributeContentV3;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RequestAttributeV3Dto extends RequestAttributeDto<BaseAttributeContentV3<?>> {

    @Override
    public int getVersion() {
        return 3;
    }



}
