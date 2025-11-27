package com.czertainly.api.model.client.metadata;

import com.czertainly.api.model.common.attribute.v3.content.BaseAttributeContentV3;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Response metadata attribute instance with content")
public class ResponseMetadataV3Dto extends ResponseMetadataDto<BaseAttributeContentV3<?>> {

}
