package com.czertainly.api.model.connector.authority;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttributeV2;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class AuthorityProviderInstanceDto extends NameAndUuidDto {

    @Schema(description = "List of Authority instance Attributes",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<BaseAttributeV2> attributes;


    public List<BaseAttributeV2> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<BaseAttributeV2> attributes) {
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
