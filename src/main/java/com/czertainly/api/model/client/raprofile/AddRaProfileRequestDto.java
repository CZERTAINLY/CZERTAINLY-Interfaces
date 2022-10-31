package com.czertainly.api.model.client.raprofile;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Class representing RA profile registration request
 */
public class AddRaProfileRequestDto {

    @Schema(description = "RA Profile name",
            required = true)
    private String name;

    @Schema(description = "RA Profile description")
    private String description;

    @Schema(description = "List of Attributes to create RA Profile",
            required = true)
    private List<RequestAttributeDto> attributes;

    @Schema(description = "Enabled flag - true = enabled; false = disabled", defaultValue = "false")
    private Boolean enabled;


    public Boolean getEnabled() {
        return enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("description", description)
                .append("attributes", attributes)
                .append("enabled", enabled)
                .toString();
    }
}
