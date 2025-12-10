package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.common.AttributeVersion;
import com.czertainly.api.model.common.attribute.common.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v3.content.BaseAttributeContentV3;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestAttributeV3 extends RequestAttribute {

    @Override
    public AttributeVersion getVersion() {
        return AttributeVersion.V3;
    }

    private UUID uuid;

    private String name;

    private AttributeContentType contentType;

    private List<BaseAttributeContentV3<?>> content;


}
