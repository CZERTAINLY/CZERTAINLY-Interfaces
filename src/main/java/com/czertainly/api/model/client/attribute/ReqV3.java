package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.common.BaseAttribute;
import com.czertainly.api.model.common.attribute.common.BaseAttributeContent;
import com.czertainly.api.model.common.attribute.v3.BaseAttributeV3;
import com.czertainly.api.model.common.attribute.v3.content.BaseAttributeContentV3;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ReqV3 {

    @Schema
    private List<BaseAttribute> attributeContentDtoV3;
}
