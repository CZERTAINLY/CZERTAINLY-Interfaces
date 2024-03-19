package com.czertainly.api.model.client.metadata;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains set of properties to represent
 * an Attribute definition including its value for the
 * detail API responses
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Response metadata attribute instance with content")
public class ResponseMetadataDto extends ResponseAttributeDto {

    @Schema(description = "Source Objects", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<NameAndUuidDto> sourceObjects = new ArrayList<>();

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("sourceObjectUuids", sourceObjects)
                .toString();
    }
}
