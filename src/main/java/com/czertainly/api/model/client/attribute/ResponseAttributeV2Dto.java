package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v2.AttributeType;
import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContentV2;
import com.czertainly.api.model.core.auth.UserWithPaginationDto;
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
public class ResponseAttributeV2Dto extends ResponseAttribute {

    private List<BaseAttributeContentV2<?>> content;

    private UUID uuid;

    private String name;

    private String label;

    private AttributeType type;

    private AttributeContentType contentType;

    @Override
    public int getVersion() {
        return 2;
    }
}
