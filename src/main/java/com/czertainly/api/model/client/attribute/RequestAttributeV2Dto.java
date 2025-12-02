package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContentV2;
import lombok.*;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestAttributeV2Dto extends RequestAttribute {


    private UUID uuid;

    private String name;

    private AttributeContentType contentType;

    private List<BaseAttributeContentV2<?>> content;


    @Override
    public int getVersion() {
        return 2;
    }
}
