package com.czertainly.api.model.client.metadata;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * This class contains set of properties to represent
 * an Attribute definition including its value for the
 * detail API responses
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Response metadata attribute instance with content")
public class ResponseMetadataDto extends ResponseAttributeDto {

    @Schema(description = "Source Object Type")
    private String sourceObjectType;

    @Schema(description = "Source Object UUID")
    private String sourceObjectUuid;

    public String getSourceObjectType() {
        return sourceObjectType;
    }

    public void setSourceObjectType(String sourceObjectType) {
        this.sourceObjectType = sourceObjectType;
    }

    public String getSourceObjectUuid() {
        return sourceObjectUuid;
    }

    public void setSourceObjectUuid(String sourceObjectUuid) {
        this.sourceObjectUuid = sourceObjectUuid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("sourceObjectType", sourceObjectType)
                .append("sourceObjectUuid", sourceObjectUuid)
                .toString();
    }
}
