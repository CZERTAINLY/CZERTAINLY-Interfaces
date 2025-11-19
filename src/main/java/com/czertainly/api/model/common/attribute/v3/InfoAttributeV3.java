package com.czertainly.api.model.common.attribute.v3;

import com.czertainly.api.model.common.attribute.v2.AttributeType;
import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v3.content.BaseAttributeContentV3;
import com.czertainly.api.model.common.attribute.v2.properties.InfoAttributeProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * This class contains set of properties to represent
 * an Attribute definition including its value for the Attributes
 * of type Info.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        description = "Info attribute contains content that is for information purpose or represents additional information for object (metadata). Its content can not be edited and is not send in requests to store.",
        type = "object"
)
@JsonDeserialize
public class InfoAttributeV3 extends BaseAttributeV3<List<BaseAttributeContentV3>> {

    /**
     * Content of the Attribute
     **/
    @Schema(
            description = "Content of the Attribute",
            requiredMode = Schema.RequiredMode.REQUIRED
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
    private InfoAttributeProperties properties;

    public InfoAttributeV3() {
        super(AttributeType.INFO);
    }

    public InfoAttributeV3(AttributeType type) {
        super(type);
    }

    public InfoAttributeV3(String type) {
        super(AttributeType.fromCode(type));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("content", content)
                .append("contentType", contentType)
                .append("properties", properties).toString();
    }
}
