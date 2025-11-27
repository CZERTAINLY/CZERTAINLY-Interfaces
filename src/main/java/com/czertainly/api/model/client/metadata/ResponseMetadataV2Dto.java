package com.czertainly.api.model.client.metadata;

import com.czertainly.api.model.client.attribute.ResponseAttributeV2Dto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContentV2;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Response metadata attribute instance with content")
public class ResponseMetadataV2Dto extends ResponseMetadataDto<BaseAttributeContentV2<?>> {


}
