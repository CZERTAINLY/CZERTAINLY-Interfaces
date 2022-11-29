package com.czertainly.api.model.client.metadata;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.fasterxml.jackson.annotation.JsonInclude;
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

    @Schema(description = "Source Object UUID")
    private List<String> sourceObjectUuids;

    public String getSourceObjectType() {
        return sourceObjectType;
    }

    public void setSourceObjectType(String sourceObjectType) {
        this.sourceObjectType = sourceObjectType;
    }

    public List<String> getSourceObjectUuids() {
        return sourceObjectUuids;
    }

    public void setSourceObjectUuids(List<String> sourceObjectUuids) {
        this.sourceObjectUuids = sourceObjectUuids;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("sourceObjectType", sourceObjectType)
                .append("sourceObjectUuid", sourceObjectUuids)
                .toString();
    }
}
