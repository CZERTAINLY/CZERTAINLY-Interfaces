package com.czertainly.api.model.core.certificate.group;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class GroupDto extends NameAndUuidDto {

    @Schema(description = "Description of the Certificate Group")
    private String description;

    @Schema(description = "List of Custom Attributes")
    private List<ResponseAttributeDto> customAttributes;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ResponseAttributeDto> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<ResponseAttributeDto> customAttributes) {
        this.customAttributes = customAttributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("description", description)
                .append("customAttributes", customAttributes)
                .toString();
    }
}
