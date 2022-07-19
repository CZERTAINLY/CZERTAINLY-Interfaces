package com.czertainly.api.model.client.location;

import com.czertainly.api.model.common.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Class representing Location edit/update request from clients
 */
public class EditLocationRequestDto {

    @Schema(
            description = "Description of the Location"
    )
    private String description;

    @Schema(
            description = "Entity instance UUID",
            required = true
    )
    private String entityInstanceUuid;

    @Schema(
            description = "List of Attributes for Location",
            required = true
    )
    private List<RequestAttributeDto> attributes;

    @Schema(
            description = "Enabled flag - true = enabled; false = disabled"
    )
    private Boolean enabled;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEntityInstanceUuid() {
        return entityInstanceUuid;
    }

    public void setEntityInstanceUuid(String entityInstanceUuid) {
        this.entityInstanceUuid = entityInstanceUuid;
    }

    public List<RequestAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<RequestAttributeDto> attributes) {
        this.attributes = attributes;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("description", description)
                .append("entityInstanceUuid", entityInstanceUuid)
                .append("attributes", attributes)
                .toString();
    }
}
