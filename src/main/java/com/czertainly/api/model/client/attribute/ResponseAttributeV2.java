package com.czertainly.api.model.client.attribute;

import com.czertainly.api.config.serializer.ResponseAttributeSerializer;
import com.czertainly.api.model.common.attribute.common.AttributeVersion;
import com.czertainly.api.model.common.attribute.common.AttributeType;
import com.czertainly.api.model.common.attribute.common.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContentV2;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
public class ResponseAttributeV2 extends ResponseAttribute {

    @JsonSerialize(using = ResponseAttributeSerializer.class)
    private List<BaseAttributeContentV2<?>> content;

    private UUID uuid;

    private String name;

    private String label;

    private AttributeType type;

    private AttributeContentType contentType;

    private AttributeVersion version = AttributeVersion.V2;

}
