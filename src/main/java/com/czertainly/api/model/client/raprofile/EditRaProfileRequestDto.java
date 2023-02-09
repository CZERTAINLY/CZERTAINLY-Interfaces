package com.czertainly.api.model.client.raprofile;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class EditRaProfileRequestDto {

    @Schema(description = "Description of RA Profile")
    private String description;

    @Schema(description = "List of Attributes for RA Profile",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<RequestAttributeDto> attributes;

    @Schema(description = "List of Custom Attributes")
    private List<RequestAttributeDto> customAttributes;

    @Schema(description = "Enabled flag - true = enabled; false = disabled")
    private Boolean enabled;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RequestAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<RequestAttributeDto> attributes) {
        this.attributes = attributes;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public List<RequestAttributeDto> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<RequestAttributeDto> customAttributes) {
        this.customAttributes = customAttributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("description", description)
                .append("attributes", attributes)
                .append("customAttributes", attributes)
                .toString();
    }
}
