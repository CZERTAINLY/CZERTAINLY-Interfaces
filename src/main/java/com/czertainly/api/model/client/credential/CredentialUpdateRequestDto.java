package com.czertainly.api.model.client.credential;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

public class CredentialUpdateRequestDto implements Serializable {

    @Schema(description = "List of Credential Attributes",
            required = true)
    private List<RequestAttributeDto> attributes;

    @Schema(description = "List of Custom Attributes")
    private List<RequestAttributeDto> customAttributes;

    public List<RequestAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<RequestAttributeDto> attributes) {
        this.attributes = attributes;
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
                .append("attributes", attributes)
                .append("customAttributes", customAttributes)
                .toString();
    }
}
