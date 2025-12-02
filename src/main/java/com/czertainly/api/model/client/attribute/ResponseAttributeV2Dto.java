package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v2.AttributeType;
import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContentV2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResponseAttributeV2Dto extends ResponseAttribute {

    private List<BaseAttributeContentV2<?>> content;

    private String uuid;

    private String name;

    private String label;

    private AttributeType type;

    private AttributeContentType contentType;

    @Override
    public int getVersion() {
        return 2;
    }
}
