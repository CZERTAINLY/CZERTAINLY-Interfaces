package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContentV2;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RequestAttributeV2Dto extends RequestAttribute {


    private String uuid;

    private String name;

    private AttributeContentType contentType;

    private List<BaseAttributeContentV2<?>> content;


    @Override
    public int getVersion() {
        return 2;
    }
}
