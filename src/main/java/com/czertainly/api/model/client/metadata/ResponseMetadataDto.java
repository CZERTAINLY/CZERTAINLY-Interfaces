package com.czertainly.api.model.client.metadata;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * This class contains set of properties to represent
 * an Attribute definition including its value for the
 * detail API responses
 */
@Schema(description = "Response metadata attribute instance with content")
public class ResponseMetadataDto extends ResponseAttributeDto {

    @Schema(description = "Source Object Type")
    private String sourceObjectType;

    @Schema(description = "Source Objects")
    private List<NameAndUuidDto> sourceObjects;

    public String getSourceObjectType() {
        return sourceObjectType;
    }

    public void setSourceObjectType(String sourceObjectType) {
        this.sourceObjectType = sourceObjectType;
    }

    public List<NameAndUuidDto> getSourceObjects() {
        return sourceObjects;
    }

    public void setSourceObjects(List<NameAndUuidDto> sourceObjectUuids) {
        this.sourceObjects = sourceObjectUuids;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("sourceObjectType", sourceObjectType)
                .append("sourceObjectUuids", sourceObjects)
                .toString();
    }
}
