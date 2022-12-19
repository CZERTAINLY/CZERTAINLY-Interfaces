package com.czertainly.api.model.connector.cryptography.token;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.common.attribute.v2.MetadataAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class TokenInstanceDto extends NameAndUuidDto {

    @Schema(
            description = "List of Token instance Attributes",
            required = true
    )
    private List<BaseAttribute> attributes;

    @Schema(
            description = "Token instance Metadata"
    )
    private List<MetadataAttribute> metadata;

    public List<BaseAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<BaseAttribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("attributes", attributes)
                .toString();
    }
}
