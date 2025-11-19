package com.czertainly.api.model.common.attribute.v3;

import com.czertainly.api.model.common.attribute.v2.AttributeType;
import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v3.content.BaseAttributeContentV3;
import com.czertainly.api.model.common.attribute.v2.properties.MetadataAttributeProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        description = "Info attribute contains content that is for metadata. Its content can not be edited and is not send in requests to store.",
        type = "object"
)
@JsonDeserialize
public class MetadataAttributeV3 extends BaseAttributeV3<List<BaseAttributeContentV3>> {

    /**
     * Content of the Attribute
     **/
    @Schema(
            description = "Content of the Attribute"
    )
    private List<BaseAttributeContentV3> content;

    /**
     * Type of the Attribute content
     */
    @Schema(
            description = "Type of the Content",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private AttributeContentType contentType;

    /**
     * Properties of the Attributes
     */
    @Schema(
            description = "Properties of the Attributes",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private MetadataAttributeProperties properties;

    public MetadataAttributeV3() {
        super(AttributeType.META);
    }

    public MetadataAttributeV3(AttributeType type) {
        super(type);
    }

    public MetadataAttributeV3(String type) {
        super(AttributeType.fromCode(type));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("content", content)
                .append("contentType", contentType)
                .append("properties", properties)
                .toString();
    }
}
