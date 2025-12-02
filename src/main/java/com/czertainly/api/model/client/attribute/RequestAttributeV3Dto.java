package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v3.content.BaseAttributeContentV3;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RequestAttributeV3Dto extends RequestAttribute {

    @Override
    public int getVersion() {
        return 3;
    }

    private String uuid;

    private String name;

    private AttributeContentType contentType;

    private List<BaseAttributeContentV3<?>> content;


}
